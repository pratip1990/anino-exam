package com.anino.leaderboardms.model;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class Entry {

	private Long score;
	private String user_id;
	private LocalDateTime scored_at;
	private Long rank;
	private String name;
}
