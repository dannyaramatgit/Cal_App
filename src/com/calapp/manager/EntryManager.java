package com.calapp.manager;

import java.util.ArrayList;
import java.util.List;
import com.calapp.cache.EntriesCache;
import com.calapp.entities.CalenderEntry;

public class EntryManager {
	private volatile static EntryManager manager;
	private EntriesCache cache = EntriesCache.getInstance();
	
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
	
	public CalenderEntry createEntry(long fromdate, long toDate, String subject, String comments){
		
		try {
			CalenderEntry newEntry = new CalenderEntry(cache.getCacheSize());
			newEntry.setComments(comments);
			newEntry.setFromFullTime(fromdate);
			newEntry.setSubject(subject);
			newEntry.setToFullTime(toDate);
			
			cache.save(newEntry);
			return newEntry;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}

	public CalenderEntry editEntry(long entryId, long fromdate, long toDate, String subject, String comments){
		try {
			CalenderEntry entry = cache.getEntry(entryId);
			entry.setComments(comments);
			entry.setFromFullTime(fromdate);
			entry.setSubject(subject);
			entry.setToFullTime(toDate);
			cache.save(entry);
			return entry;
		} catch (Exception e) {
			System.err.println("Failed to save entry: "+entryId);
		}
		
		return null;
	}
	
	public void deleteEntry(long entryId){
		cache.delete(entryId);
		
	}
	
	public CalenderEntry getEntry(long fromDate, long toDate){
		CalenderEntry result = null;
		return result;
	}
	
	public List<CalenderEntry> getEntries(long fromDate, long toDate){
		List<CalenderEntry> results = new ArrayList<CalenderEntry>();
		
		return results;
	}

	public CalenderEntry getEntry(long entryId) {
		return cache.getEntry(entryId);
	}
}
