package com.calapp.persistance;

import java.util.Collection;

import com.calapp.entities.CalenderEntry;

public interface PersistanceIfc {

	long getCacheSize();

	CalenderEntry getEntry(long entryId);

	void save(CalenderEntry entry);

	void delete(long id) throws Exception;

	Collection<String> getEntries();

}