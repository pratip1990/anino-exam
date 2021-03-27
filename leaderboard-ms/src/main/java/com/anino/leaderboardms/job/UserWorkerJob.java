package com.anino.leaderboardms.job;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.anino.leaderboardms.service.leaderboard.LeaderboardWorkerServiceImpl;

@Component
public class UserWorkerJob {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserWorkerJob.class);
	
	@Autowired
	LeaderboardWorkerServiceImpl leaderboardWorkerService;
	
	@Scheduled(cron = "0/10 * * * * *")
	public void dummyUserWorkerScheduler() {
		LOGGER.info("LeaderboardWorker is running :: {}", LocalDateTime.now());
		
		leaderboardWorkerService.triggerDummyUserWorker();
	}
}
