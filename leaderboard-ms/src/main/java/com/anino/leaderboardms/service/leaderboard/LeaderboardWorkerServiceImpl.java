package com.anino.leaderboardms.service.leaderboard;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.anino.leaderboardms.model.Board;
import com.anino.leaderboardms.repository.LeaderboardRepository;

@Service
public class LeaderboardWorkerServiceImpl {
	@Autowired
	private LeaderboardRepository leaderboardRepository;

	public void triggerDummyUserWorker() {
		
		List<Board> board = leaderboardRepository.findAll();
		
	}
}
