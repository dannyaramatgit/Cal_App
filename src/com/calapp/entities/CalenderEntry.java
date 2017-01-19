package com.calapp.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalenderEntry {
	
	private long entryId;
	private String toTime;
	private String fromTime;
	private String subject;
	private String comments;
	
	 public CalenderEntry() {
	    }
	
	public CalenderEntry(long entryId){
		this.entryId=entryId;
	}
	
	public CalenderEntry(long entryId, String fromTime, String toTime, String subject, String comments) {
		this.entryId = entryId;
		this.subject = subject;
		this.comments = comments;
		this.fromTime = fromTime;
		this.toTime = toTime;
	}

	public long getEntryId() {
		return entryId;
	}

	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	
	public String getToTime() {
		return toTime;
	}

	public void setToTime(String toTime) {
		this.toTime = toTime;
	}

	public String getFromTime() {
		return fromTime;
	}

	public void setFromTime(String fromTime) {
		this.fromTime = fromTime;
	}

	public String toString(){
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(this);
			return jsonInString;
		} catch (JsonProcessingException e) {
			System.err.println("Failed to parse object to JSON. Id:" + getEntryId());
			return null;
		}
	}
}
