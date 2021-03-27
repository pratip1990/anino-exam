package com.anino.leaderboardms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anino.leaderboardms.model.Board;

public interface LeaderboardRepository extends MongoRepository<Board, String>{

}
