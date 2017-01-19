package com.calapp.entities;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class CalenderEntry {
	
	private long entryId;
	private long fromFullTime;
	private long toFullTime;
	private String subject;
	private String comments;
	
	 public CalenderEntry() {
	    }
	
	public CalenderEntry(long entryId){
		this.entryId=entryId;
	}
	
	public CalenderEntry(long entryId, long fromFullTime, long toFullTime, String subject, String comments) {
		this.entryId = entryId;
		this.fromFullTime = fromFullTime;
		this.toFullTime = toFullTime;
		this.subject = subject;
		this.comments = comments;
	}

	public long getEntryId() {
		return entryId;
	}

	public long getFromFullTime() {
		return fromFullTime;
	}
	public void setFromFullTime(long fromFullTime) {
		this.fromFullTime = fromFullTime;
	}
	public long getToFullTime() {
		return toFullTime;
	}
	public void setToFullTime(long toFullTime) {
		this.toFullTime = toFullTime;
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
	
	
	public String toString(){
		try {
			ObjectMapper mapper = new ObjectMapper();
			String jsonInString = mapper.writeValueAsString(this);
			return jsonInString;
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	

}
