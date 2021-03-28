package com.anino.leaderboardms.service.leaderboard;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anino.leaderboardms.exception.InvalidParameterException;
import com.anino.leaderboardms.model.Board;
import com.anino.leaderboardms.model.Entry;
import com.anino.leaderboardms.model.User;
import com.anino.leaderboardms.repository.UserRepository;
import com.anino.leaderboardms.request.ScoreToAddReq;

@Service
public class LeaderboardWorkerServiceImpl {

	@Autowired
	private LeaderboardServiceImpl leaderboardService;

	@Autowired
	private UserRepository userRepository;

	public void triggerDummyUserWorker(List<Board> boardList, Map<String, String> fakeUserMap)
			throws InvalidParameterException {

		if (boardList != null && !boardList.isEmpty()) {
			for (Board board : boardList) {
				if (board.getEntries() != null && !board.getEntries().isEmpty()) {
					for (Entry entry : board.getEntries()) {

						if (entry.getName().startsWith("FakeUser") && entry.getRank() != 0) {
							// if (entry.getName().startsWith("FakeUser")) {
							ScoreToAddReq req = new ScoreToAddReq();
							req.setScore_to_add(10L);
							leaderboardService.scoreToAdd(board.getId(), entry.getUser_id(), req);
							break;

							// }

						} else if (!fakeUserMap.containsKey(board.getId())) {
							User user = new User();
							user.setName("FakeUser_" + board.getId());
							user = userRepository.save(user);

							ScoreToAddReq req = new ScoreToAddReq();
							req.setScore_to_add(10L);
							leaderboardService.scoreToAdd(board.getId(), user.getId(), req);
							break;
						}

					}
				}
			}
		}
	}

}
