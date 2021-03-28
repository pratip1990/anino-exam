package com.anino.leaderboardms.job;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anino.leaderboardms.exception.InvalidParameterException;
import com.anino.leaderboardms.model.Board;
import com.anino.leaderboardms.model.User;
import com.anino.leaderboardms.repository.LeaderboardRepository;
import com.anino.leaderboardms.repository.UserRepository;
import com.anino.leaderboardms.service.leaderboard.LeaderboardWorkerServiceImpl;

@Component
public class UserWorkerJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserWorkerJob.class);

	@Autowired
	LeaderboardWorkerServiceImpl leaderboardWorkerService;

	@Autowired
	private LeaderboardRepository leaderboardRepository;

	@Autowired
	private UserRepository userRepository;

	private List<Board> boardList;
	private Map<String, String> fakeUserMap;

	@PostConstruct
	public void init() {
		boardList = leaderboardRepository.findAll();
		fakeUserMap = new HashedMap<String, String>();

		List<User> currentFakeUserList = userRepository.findAll();
		if (currentFakeUserList != null && !currentFakeUserList.isEmpty()) {

			currentFakeUserList.forEach(usr -> {
				if (usr.getName().startsWith("FakeUser_")) {
					fakeUserMap.put(usr.getName().split("_")[1], usr.getId());
				}
			});
		}

	}

	@Scheduled(cron = "0/5 * * * * *")
	public void dummyUserWorkerScheduler() {
		LOGGER.info("LeaderboardWorker is running :: {}", LocalDateTime.now());

		try {
			leaderboardWorkerService.triggerDummyUserWorker(boardList, fakeUserMap);
		} catch (InvalidParameterException e) {
			e.printStackTrace();
		}
	}

	@Scheduled(cron = "0/6 * * * * *")
	public void updateLeaderboardListScheduler() {
		LOGGER.info("UpdateLeaderboardListWorker is running :: {}", LocalDateTime.now());
		boardList = leaderboardRepository.findAll();

		fakeUserMap = new HashedMap<String, String>();
		List<User> currentFakeUserList = userRepository.findAll();
		if (currentFakeUserList != null && !currentFakeUserList.isEmpty()) {

			currentFakeUserList.forEach(usr -> {
				if (usr.getName().startsWith("FakeUser_")) {
					fakeUserMap.put(usr.getName().split("_")[1], usr.getId());
				}
			});
		}

	}
}
