package com.calapp.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.util.Calendar;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.calapp.entities.CalenderEntry;
import com.calapp.manager.EntryManager;
import com.calapp.manager.Utilities;


public class EntityManagerTests {

	private EntryManager manager;
	String fromdate;
	String toDate;
	String subject = "new entry subject ";
	String comments = "new entry comment ";
	
	static int entryCount= 0 ;
	
	
	@Before
	public void setup(){
		manager=EntryManager.getInstance();
		Calendar calendar = Calendar.getInstance();
		prepareEntryData(calendar, 1);
	}

	private void prepareEntryData(Calendar calendar, int hoursAdded) {
		fromdate = Utilities.longToDateString(calendar.getTime().getTime());
		calendar.add(Calendar.HOUR, hoursAdded);
		toDate = Utilities.longToDateString(calendar.getTime().getTime());
	}
	
	@Test
	public void testNewEntry(){
		CalenderEntry newEntry = manager.createEntry(fromdate,toDate , subject + entryCount, comments + entryCount );
		
		assertNotNull(newEntry);
		assertNotNull(newEntry.getComments());
		assertNotNull(newEntry.getEntryId());
		assertNotNull(newEntry.getFromTime());
		assertNotNull(newEntry.getSubject());
		assertNotNull(newEntry.getToTime());
		entryCount++;
	}
	
	@Test
	public void testEditEntry(){
		CalenderEntry newEntry = manager.createEntry(fromdate,toDate , subject, comments );
		String newComments = "new comments";
		newEntry.setComments(newComments);
		CalenderEntry  edited = manager.editEntry(newEntry.getEntryId(), newEntry.getFromTime(), newEntry.getToTime(), newEntry.getSubject(), newComments);
		
		assertEquals(newComments, edited.getComments());
		
	}
	
	@Test
	public void testDeleteEntry(){
		CalenderEntry newEntry = manager.createEntry(fromdate,toDate , subject, comments );
		manager.deleteEntry(newEntry.getEntryId());
		CalenderEntry fetched = manager.getEntry(newEntry.getEntryId());
		assertNull(fetched);
	}
	
	@Test
	public void testGetListByDates(){
		Calendar calendar = Calendar.getInstance();
		for(int i=0; i<10; i++){
			calendar.add(Calendar.HOUR, 1);
			calendar.add(Calendar.MINUTE, 20);
			prepareEntryData(calendar, 1);
			 manager.createEntry(fromdate, toDate, subject + entryCount, comments + entryCount);
			entryCount++;
		}
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 3);
		prepareEntryData(calendar, 3);
		List<CalenderEntry> list = manager.getEntriesByDates(fromdate, toDate);
		
		assertNotNull(list);
		assertNotEquals("search bigger than 0", 0, list.size());
		
		
	}
	
	@Test
	public void testSearchByText(){
		Calendar calendar = Calendar.getInstance();
		for(int i=1; i<13; i++){
			calendar.add(Calendar.HOUR, 1);
			calendar.add(Calendar.MINUTE, 20);
			prepareEntryData(calendar, 1);
			
			String newSubject = subject;
			if(i% 4 == 0){
				newSubject = newSubject.concat(" Quater ");
			}
			if(i % 3 == 0){
				newSubject = newSubject.concat(" triple ");
			}
			if(i % 2 == 0){
				newSubject = newSubject.concat(" double");
			}
			newSubject = newSubject.concat(" BREAKKKKKK ");
			
			manager.createEntry(fromdate, toDate, newSubject, comments + entryCount);
			entryCount++;
		}
		
		calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, 3);
		prepareEntryData(calendar, 3);
		List<CalenderEntry> list = manager.getEntriesByText("Quater");
				
		assertNotNull(list);
		assertEquals("search Quater", 3, list.size());
		
		list = manager.getEntriesByText("triple");
		
		assertNotNull(list);
		assertEquals("search triple", 4, list.size());
		
		list = manager.getEntriesByText("double");
		
		assertNotNull(list);
		assertEquals("search bigger than 0", 6, list.size());
		
		list = manager.getEntriesByText("BREAK");
		
		assertNotNull(list);
		assertEquals("search double", 12, list.size());
	}

}
