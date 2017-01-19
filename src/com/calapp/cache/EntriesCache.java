package com.calapp.cache;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.calapp.entities.CalenderEntry;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntriesCache {
	
	private volatile static EntriesCache cache;
	
	private Map<Long, String> entriesCache= new HashMap<Long, String>();
	
	private EntriesCache(){
		
	}

	public static EntriesCache getInstance(){
		
		if (cache == null) {
            synchronized (EntriesCache.class) {
                if (cache == null) {
                	cache = new EntriesCache();
                }
            }
        }
        return cache;
		
	}

	public long getCacheSize() {
		return entriesCache.size();

	}

	public CalenderEntry getEntry(long entryId) {
		ObjectMapper mapper = new ObjectMapper();
		String resultStr = cache.entriesCache.get(entryId);
		
		CalenderEntry entry = null;
		if (!StringUtils.isEmpty(resultStr)) {
			try {
				entry = mapper.readValue(resultStr, CalenderEntry.class);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return entry;
	}

	public void save(CalenderEntry entry) {
		cache.entriesCache.put(entry.getEntryId(), entry.toString());
		
	}
	
	public void delete(long id){
		cache.entriesCache.remove(id);
	}
	
	
}
