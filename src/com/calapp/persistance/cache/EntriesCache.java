package com.calapp.persistance.cache;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.calapp.entities.CalenderEntry;
import com.calapp.persistance.PersistanceIfc;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class EntriesCache implements PersistanceIfc {
	
	private volatile static EntriesCache cache;
	
	private Map<Long, String> entriesCache= new HashMap<Long, String>();
	
	private EntriesCache(){
		
	}

	public static PersistanceIfc getInstance(){
		
		if (cache == null) {
            synchronized (EntriesCache.class) {
                if (cache == null) {
                	cache = new EntriesCache();
                }
            }
        }
        return cache;
		
	}

	/* (non-Javadoc)
	 * @see com.calapp.cache.PersistanceIfc#getCacheSize()
	 */
	@Override
	public long getCacheSize() {
		return entriesCache.size();

	}

	/* (non-Javadoc)
	 * @see com.calapp.cache.PersistanceIfc#getEntry(long)
	 */
	@Override
	public CalenderEntry getEntry(long entryId) {
		ObjectMapper mapper = new ObjectMapper();
		String resultStr = cache.entriesCache.get(entryId);
		
		CalenderEntry entry = null;
		if (!StringUtils.isEmpty(resultStr)) {
			try {
				entry = mapper.readValue(resultStr, CalenderEntry.class);
			} catch (JsonParseException e) {
				System.err.println("Failed to parse entity: " + resultStr);
			} catch (JsonMappingException e) {
				System.err.println("Failed to map entity: " + resultStr);
			} catch (IOException e) {
				System.err.println("Reading entity exception.");
			}
		}
		
		return entry;
	}

	/* (non-Javadoc)
	 * @see com.calapp.cache.PersistanceIfc#save(com.calapp.entities.CalenderEntry)
	 */
	@Override
	public void save(CalenderEntry entry) {
		cache.entriesCache.put(entry.getEntryId(), entry.toString());
		
	}
	
	/* (non-Javadoc)
	 * @see com.calapp.cache.PersistanceIfc#delete(long)
	 */
	@Override
	public void delete(long id) throws Exception{
		cache.entriesCache.remove(id);
	}

	/* (non-Javadoc)
	 * @see com.calapp.cache.PersistanceIfc#getEntries()
	 */
	@Override
	public  Collection<String> getEntries() {
		return entriesCache.values();
		
	}
	
	
}
