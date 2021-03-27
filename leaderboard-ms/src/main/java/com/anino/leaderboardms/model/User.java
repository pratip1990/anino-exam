package com.anino.leaderboardms.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Document(collection = "users")
public class User {
	
	@JsonProperty("_id")
	@Id
	private String id;
	private String name;
}
