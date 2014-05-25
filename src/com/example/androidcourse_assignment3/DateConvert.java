package com.example.androidcourse_assignment3;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/** Converts Date objects to and from various string formats. */
public class DateConvert {
	private final static String SHORT_PATTERN = "M/d/y";
	private final static String SQL_PATTERN = "yyyy-MM-dd HH:mm:ss";
	
	/** Returns an arbitrary date to be used as a "default" date. */
	public static Date Default() {
		return new Date();
	}
	
	/** Converts a string of the form "month/day/year" to a Date object. */
	public static Date FromShortString(String string) {
		return FromString(string, SHORT_PATTERN);
	}
	
	/** Converts a string of the form "year/month/day hour:minute:second" to a Date object. */
	public static Date FromSqlString(String string) {
		return FromString(string, SQL_PATTERN);
	}
	
	/** Converts a Date object to a string of the form "month/day/year". */
	public static String ToShortString(Date date){
		return ToString(date, SHORT_PATTERN);
	}
	
	/** Converts a Date object to a string of the form "year/month/day hour:minute:second". */
	public static String ToSqlString(Date date){
		return ToString(date, SQL_PATTERN);
	}
	
	static Date FromString(String string, String pattern) {
		Date date = null;
		if(string != null) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			try {
				date = format.parse(string);
			} catch (ParseException e) {
				e.printStackTrace();
				date = new Date(0);
			}
		}
		return date;
	}
	
	public static String ToString(Date date, String pattern){
		String string = null;
		if(date != null) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			string = format.format(date);
		}
		return string;
	}
}

