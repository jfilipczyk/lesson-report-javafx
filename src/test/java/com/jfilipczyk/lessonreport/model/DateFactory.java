package com.jfilipczyk.lessonreport.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateFactory {

    private final static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
        
    public static Date create(String dateString) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
            sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
            return sdf.parse(dateString);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }
}
