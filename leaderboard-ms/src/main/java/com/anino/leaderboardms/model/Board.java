package com.anino.leaderboardms.model;

import java.util.LinkedList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
@Document(collection = "leaderboards")
@JsonInclude(Include.NON_NULL)
public class Board {
	
	@JsonProperty("_id")
	@Id
	private String id;
	private String name;
	
	@JsonProperty(required = false)
	private List<Entry> entries;
	
	public void setEntries(List<Entry> entries) {
		List<Entry> tempEntries = new LinkedList<Entry>();
		for (Entry entry : entries) {
			tempEntries.add(entry);
		}
		
		this.entries = tempEntries;
	}
}
