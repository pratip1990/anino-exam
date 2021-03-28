package com.anino.leaderboardms.service.leaderboard;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anino.leaderboardms.exception.InvalidParameterException;
import com.anino.leaderboardms.exception.NoDataFoundException;
import com.anino.leaderboardms.model.Board;
import com.anino.leaderboardms.model.Entry;
import com.anino.leaderboardms.model.Leaderboard;
import com.anino.leaderboardms.model.User;
import com.anino.leaderboardms.repository.LeaderboardRepository;
import com.anino.leaderboardms.repository.UserRepository;
import com.anino.leaderboardms.request.ScoreToAddReq;

@Service
public class LeaderboardServiceImpl {

	private static final Logger LOGGER = LoggerFactory.getLogger(LeaderboardServiceImpl.class);

	@Autowired
	private LeaderboardRepository leaderboardRepository;

	@Autowired
	private UserRepository userRepository;

	private Map<String, Entry> topEntryMap;

	@PostConstruct
	public void init() {
		topEntryMap = new HashedMap<String, Entry>();
		List<Board> board = leaderboardRepository.findAll();

		board.forEach(brd -> {
			if (brd.getEntries() != null && !brd.getEntries().isEmpty()) {
				topEntryMap.put(brd.getId(), brd.getEntries().get(0));
			}
		});

	}

	public Leaderboard saveNewLeaderboard(Board board) {
		Leaderboard leaderboard = null;
		board = leaderboardRepository.save(board);
		if (board != null) {
			new Leaderboard();
			leaderboard = new Leaderboard();
			leaderboard.setBoard(board);
		} else {
			throw new RuntimeException("Something went wrong");
		}
		return leaderboard;
	}

	public Entry scoreToAdd(String id, String userId, ScoreToAddReq req) throws InvalidParameterException {
		Entry entryRes = null;
		boolean updateSuccesFlg = false;

		Optional<Board> board = leaderboardRepository.findById(id);
		Optional<User> user = userRepository.findById(userId);

		if (board.isPresent() && user.isPresent()) {

			if (null != board.get().getEntries() && !board.get().getEntries().isEmpty()) {
				for (Entry entry : board.get().getEntries()) {
					if (entry.getUser_id().equals(userId)) {
						// entry.setScore(req.getScore_to_add());
						entry.setScore(entry.getScore() + req.getScore_to_add());
						entry.setScored_at(LocalDateTime.now());
						entryRes = entry;
						updateSuccesFlg = true;
						break;
					}
				}
			}

			if (!updateSuccesFlg) {
				Entry entry = new Entry();
				entry.setName(user.get().getName());
				entry.setUser_id(user.get().getId());
				entry.setScore(req.getScore_to_add());
				entry.setScored_at(LocalDateTime.now());

				List<Entry> entries = new LinkedList<Entry>();
				if (board.get().getEntries() == null || board.get().getEntries().isEmpty()) {
					entries = new LinkedList<Entry>();
					entry.setRank(0L);
					topEntryMap.put(id, entry);
				} else {
					entries = board.get().getEntries();
					entry.setRank(Long.valueOf(board.get().getEntries().size()));
				}
				entries.add(entry);
				board.get().setEntries(entries);
				entryRes = entry;
				updateSuccesFlg = true;
			}

			if (updateSuccesFlg) {

				Comparator<Entry> scoreComparator = (e1, e2) -> e1.getScore().compareTo(e2.getScore());
				Comparator<Entry> timeComparator = (e1, e2) -> e1.getScored_at().compareTo(e2.getScored_at());
				Comparator<Entry> scoreAndTimeComarator = scoreComparator.reversed().thenComparing(timeComparator);
				board.get().getEntries().sort(scoreAndTimeComarator);
				long cnt = 0L;
				for (Entry entry : board.get().getEntries()) {
					entry.setRank(cnt);
					cnt += 1;
					if(entry.getUser_id().equals(entryRes.getUser_id())) {
						entryRes = entry;
					}
				}

				leaderboardRepository.save(board.get());
				updateUserBoardRank(id, entryRes);
				return entryRes;
			}

		} else {
			throw new InvalidParameterException("Invalid Board Id or User Id");
		}

		throw new RuntimeException("Failed to update the Score");
	}

	private void updateUserBoardRank(String id, Entry entry) {

		Thread t1 = new Thread() {
			public void run()
			{
				if (topEntryMap.containsKey(id)) {
					Entry currentTopEntry = topEntryMap.get(id);
					if(!currentTopEntry.getUser_id().equals(entry.getUser_id()) && currentTopEntry.getScore() < entry.getScore()) {
						topEntryMap.put(id, entry);
						LOGGER.info("<< 1st Rank just changed >>> Board No: {} Entry Details {}",id, entry);
					}
				}		
			}

		};
		t1.start();

	}

	public Leaderboard getLeaderboardData(String id, String perPage, String page)
			throws NoDataFoundException, InvalidParameterException {

		int perPageInt = 1;
		int pageNoInt = 10;
		try {
			perPageInt = Integer.valueOf(perPage);
			pageNoInt = Integer.valueOf(page);
		} catch (Exception e) {
			perPageInt = 1;
			pageNoInt = 10;
		}

		Optional<Board> board = leaderboardRepository.findById(id);

		if (board.isPresent()) {
			
			List<Entry> filteredList = convertToPaginationList(board.get().getEntries(), perPageInt, pageNoInt);

			if (filteredList == null || filteredList.isEmpty()) {
				throw new NoDataFoundException("No data found");
			} else {
				board.get().setEntries(filteredList);
			}

			Leaderboard leaderboard = new Leaderboard();
			leaderboard.setBoard(board.get());
			return leaderboard;
		} else {
			throw new InvalidParameterException("Invalid Board Id");
		}

	}

	private List<Entry> convertToPaginationList(List<Entry> entries, int perPageInt, int pageNoInt) {
		List<List<Entry>> allLsits = ListUtils.partition(entries, perPageInt);

		if (allLsits != null && !allLsits.isEmpty() && pageNoInt <= allLsits.size()) {
			if (pageNoInt == 1) {
				return allLsits.get(0);
			} else {
				return allLsits.get(pageNoInt - 1);
			}
		}
		return null;
	}

}
