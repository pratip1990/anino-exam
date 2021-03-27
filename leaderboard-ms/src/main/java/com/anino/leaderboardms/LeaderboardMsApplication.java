package com.anino.leaderboardms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LeaderboardMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(LeaderboardMsApplication.class, args);
	}

}
