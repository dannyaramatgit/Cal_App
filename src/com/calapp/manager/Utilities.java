package com.calapp.manager;

import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class Utilities {
	
	private static DateTimeFormatter format = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm");

	public static long dateStringToLong(String dateStr) throws DateTimeParseException{
		try {
			DateTime time = format.parseDateTime(dateStr);
			return time.getMillis();
		} catch (Exception e) {
			throw new DateTimeParseException("Fialed to parse date", dateStr, 0 );
		}
	}
	
	public static String longToDateString(long lngTime){
		DateTime time = new DateTime(lngTime);
		return format.print(time);
	}
	
	
	/**
	 * validates from date smaller than to date
	 * @param from
	 * @param to
	 * @return pass validation
	 */
	public static boolean validatedates(String from, String to){
		try {
			if (!StringUtils.isEmpty(from) && !StringUtils.isEmpty(to)) {
				DateTime fromTime = format.parseDateTime(from);
				DateTime toTime = format.parseDateTime(to);
				if (fromTime.getMillis() <= toTime.getMillis()) {
					throw new DateTimeParseException("From date must be before to date.", from + "---" + to, 0);
				} 
			}
			return true;
		} catch (Exception e) {
			throw new DateTimeParseException("Failed to parse dates", from + "---" + to, 0 );
		}
	}
	
		/**
		 * validates the date string is in the right format
		 * @param date
		 * @return pass validation
		 */
		public static boolean validateDate(String date){
		try{
			if (!StringUtils.isEmpty(date)) {
				@SuppressWarnings("unused")
				DateTime time = format.parseDateTime(date);
			}
			return true;
		}catch(Exception e){
			System.err.println(date + " does not match the required date formet of: yyyy-MM-dd'T'HH:mm");
			return false;
		}
		
	}
	
}
