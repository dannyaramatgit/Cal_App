package com.calapp.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.calapp.entities.CalenderEntry;
import com.calapp.persistance.PersistanceIfc;
import com.calapp.persistance.cache.EntriesCache;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author danny
 * 
 * Entry manipulation
 *
 */
public class EntryManager {
	private volatile static EntryManager manager;
	private PersistanceIfc cache = EntriesCache.getInstance();
	
	private EntryManager(){
		
	}
	
	public static EntryManager getInstance(){
		
		if (manager == null) {
            synchronized (EntryManager.class) {
                if (manager == null) {
                	manager = new EntryManager();
                }
            }
        }
        return manager;
	}
	
	public CalenderEntry createEntry(CalenderEntry newEntry){
		cache.save(newEntry);
		return newEntry;
	}
	
	
	public CalenderEntry createEntry(String fromdate, String toDate, String subject, String comments){
		
		try {
			CalenderEntry newEntry = new CalenderEntry(cache.getCacheSize()+1);
			fromdate = fixFromDate(fromdate, toDate);
			toDate = fixToDate(fromdate, toDate);
			newEntry.setComments(comments);
			newEntry.setFromTime(fromdate);
			newEntry.setSubject(subject);
			newEntry.setToTime(toDate);
			
			cache.save(newEntry);
			return newEntry;
		} catch (Exception e) {
			System.err.println("Failed to create new Entry: " + subject);
		}
		
		return null;
	}

	public CalenderEntry editEntry(CalenderEntry editedEntry){
		return editEntry(editedEntry.getEntryId(), editedEntry.getFromTime(), 
				editedEntry.getFromTime(), editedEntry.getSubject(), editedEntry.getComments());
	}
	
	public CalenderEntry editEntry(long entryId, String fromdate, String toDate, String subject, String comments){
		try {
			fromdate = fixFromDate(fromdate, toDate);
			toDate = fixToDate(fromdate, toDate);
			
			CalenderEntry entry = cache.getEntry(entryId);
			entry.setComments(comments);
			entry.setFromTime(fromdate);
			entry.setSubject(subject);
			entry.setToTime(toDate);
			cache.save(entry);
			return entry;
		} catch (Exception e) {
			System.err.println("Failed to save entry: "+entryId);
		}
		
		return null;
	}
	
	/**
	 * make from date leagal
	 * @param fromdate
	 * @param toDate
	 * @return fix fromDate
	 */
	private String fixFromDate(String fromdate, String toDate) {
		if(StringUtils.isEmpty(fromdate) && !StringUtils.isEmpty(toDate)){
			DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm");
				DateTime toTime = format.parseDateTime(toDate);
				DateTime fromTime = toTime.minusHours(1);
				return Utilities.longToDateString(fromTime.getMillis());
		}
		if(StringUtils.isEmpty(fromdate) && StringUtils.isEmpty(toDate)){
			DateTime dateTime = new DateTime();
			return Utilities.longToDateString(dateTime.getMillis());
		}
		return fromdate;
		
	}
	
	/**
	 * make to date leagal
	 * @param fromdate
	 * @param toDate
	 * @return fixed to date
	 */
	private String fixToDate(String fromdate, String toDate) {
		DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm");
		if(StringUtils.isEmpty(toDate) ){
			DateTime fromTime = format.parseDateTime(fromdate);
			DateTime toTime = fromTime.plusHours(1);
			return Utilities.longToDateString(toTime.getMillis());
		}
		
		
		return toDate;
	}

	public boolean deleteEntry(long entryId){
		try {
			cache.delete(entryId);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	public Collection<String> getEntriesAsStrings(long fromDate, long toDate){
		return cache.getEntries();
	}
	
	public List<CalenderEntry> getEntriesByDates(String fromDateStr, String toDateStr){
		List<CalenderEntry> results = new ArrayList<CalenderEntry>();
		ObjectMapper mapper = new ObjectMapper();
		
		long fromDate = Utilities.dateStringToLong(fromDateStr);
		long toDate = Utilities.dateStringToLong(toDateStr);
		
		List<String> cacheEntries = new ArrayList<String>(); 
		cacheEntries.addAll(cache.getEntries());
		
		for( String entryStr: cacheEntries){
			try {
				CalenderEntry entry = mapper.readValue(entryStr, CalenderEntry.class);
				long fromTime = Utilities.dateStringToLong(entry.getFromTime());
				if(fromTime >= fromDate && fromTime <= toDate){
					results.add(entry);
				}
			} catch (JsonParseException e) {
				System.err.println("Failed to parse entity: " + entryStr);
			} catch (JsonMappingException e) {
				System.err.println("Failed to map entity: " + entryStr);
			} catch (IOException e) {
				System.err.println("Reading entity exception.");
			}
		}
		
		return results;
	}

	public CalenderEntry getEntry(long entryId) {
		return cache.getEntry(entryId);
	}

	public List<CalenderEntry> getEntriesByText(String searchString) {
		List<CalenderEntry> results = new ArrayList<CalenderEntry>();
		ObjectMapper mapper = new ObjectMapper();
		
		List<String> cacheEntries = new ArrayList<String>(); 
		cacheEntries.addAll(cache.getEntries());
		
		for( String entryStr: cacheEntries){
			try {
				CalenderEntry entry = mapper.readValue(entryStr, CalenderEntry.class);
				if(!StringUtils.isEmpty(entry.getSubject()) && entry.getSubject().contains(searchString) ||
						!StringUtils.isEmpty(entry.getComments()) && entry.getComments().contains(searchString)){
					results.add(entry);
				}
			} catch (JsonParseException e) {
				System.err.println("Failed to parse entity: " + entryStr);
			} catch (JsonMappingException e) {
				System.err.println("Failed to map entity: " + entryStr);
			} catch (IOException e) {
				System.err.println("Reading entity exception.");
			}
		}
		
		return results;
	}
}
