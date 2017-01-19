package com.calapp.tests;

import static org.junit.Assert.*;

import java.sql.Date;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.calapp.entities.CalenderEntry;
import com.calapp.manager.EntryManager;


public class EntityManagerTests {

	private EntryManager manager;
	long fromdate;
	long toDate;
	String subject = "new entry subject";
	String comments = "new entry comment";
	
//	@Test
	public void test() {
		fail("Not yet implemented");
	}
	
	
	@Before
	public void setup(){
		manager=EntryManager.getInstance();
		Calendar calendar = Calendar.getInstance();
		fromdate = calendar.getTime().getTime();
		calendar.add(Calendar.HOUR, 1);
		toDate = calendar.getTime().getTime();
		
	}
	
	@Test
	public void testNewEntry(){
		CalenderEntry newEntry = manager.createEntry(fromdate,toDate , subject, comments );
		
		assertNotNull(newEntry);
		assertNotNull(newEntry.getComments());
		assertNotNull(newEntry.getEntryId());
		assertNotNull(newEntry.getFromFullTime());
		assertNotNull(newEntry.getSubject());
		assertNotNull(newEntry.getToFullTime());
	}
	
	@Test
	public void testEditEntry(){
		CalenderEntry newEntry = manager.createEntry(fromdate,toDate , subject, comments );
		String newComments = "new comments";
		newEntry.setComments(newComments);
		CalenderEntry  edited = manager.editEntry(newEntry.getEntryId(), newEntry.getFromFullTime(), newEntry.getToFullTime(), newEntry.getSubject(), newComments);
		
		assertEquals(newComments, edited.getComments());
		
	}
	
	@Test
	public void testDeleteEntry(){
		CalenderEntry newEntry = manager.createEntry(fromdate,toDate , subject, comments );
		manager.deleteEntry(newEntry.getEntryId());
		CalenderEntry fetched = manager.getEntry(newEntry.getEntryId());
		assertNull(fetched);
	}

}
