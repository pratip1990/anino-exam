package com.anino.leaderboardms.controller.leaderboard;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anino.leaderboardms.exception.InvalidParameterException;
import com.anino.leaderboardms.exception.NoDataFoundException;
import com.anino.leaderboardms.model.Board;
import com.anino.leaderboardms.model.Entry;
import com.anino.leaderboardms.model.Leaderboard;
import com.anino.leaderboardms.request.ScoreToAddReq;
import com.anino.leaderboardms.service.leaderboard.LeaderboardServiceImpl;

@RestController
public class LeaderboardController {

	@Autowired
	private LeaderboardServiceImpl leaderboardService;

	@PostMapping(value = "/admin/leaderboard", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Leaderboard> addNewLeaderboard(@RequestBody Board board) {
		Leaderboard leaderboard = leaderboardService.saveNewLeaderboard(board);
		return new ResponseEntity<Leaderboard>(leaderboard, HttpStatus.OK);
	}

	@PutMapping(value = "/leaderboard/{_id}/user/{user_id}/add_score", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Entry> scoreToAdd(@PathVariable(value = "_id") String id,
			@PathVariable(value = "user_id") String userId, @RequestBody ScoreToAddReq req)
			throws InvalidParameterException {
		Entry entry = leaderboardService.scoreToAdd(id, userId, req);
		return new ResponseEntity<Entry>(entry, HttpStatus.OK);
	}

	@GetMapping(value = "/leaderboard/{_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Leaderboard> getBoard(@PathVariable(value = "_id") String id,
			@RequestParam(value = "per_page", required = false, defaultValue = "10") String perPage,
			@RequestParam(value = "page", required = false, defaultValue = "1") String page)
			throws NoDataFoundException, InvalidParameterException {

		Leaderboard leaderboard = leaderboardService.getLeaderboardData(id, perPage, page);
		return new ResponseEntity<Leaderboard>(leaderboard, HttpStatus.OK);

	}
}
