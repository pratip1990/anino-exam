package com.anino.leaderboardms.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.anino.leaderboardms.model.User;

public interface UserRepository extends MongoRepository<User, String>{

}
