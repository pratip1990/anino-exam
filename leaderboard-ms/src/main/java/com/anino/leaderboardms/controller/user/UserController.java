package com.anino.leaderboardms.controller.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.anino.leaderboardms.exception.NoDataFoundException;
import com.anino.leaderboardms.model.User;
import com.anino.leaderboardms.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	UserRepository userRepository;
	
	@GetMapping("/status")
	public ResponseEntity<String> status(){
		return new ResponseEntity<String>("{}", HttpStatus.OK);	
	}
	
	@PostMapping(value = "/user", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addNewUser(@RequestBody User user){
		try {
			user = userRepository.save(user);
			return new ResponseEntity<User>(user, HttpStatus.OK);
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		
	}
	
	@GetMapping(value = "/user/{_id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUserById(@PathVariable(value = "_id") String id) throws NoDataFoundException{
		
		Optional<User> user = userRepository.findById(id);
		if(!user.isPresent()) {
			throw new NoDataFoundException("No Data found");
		}
		return new ResponseEntity<User>(user.get(), HttpStatus.OK);
		
	}
}
