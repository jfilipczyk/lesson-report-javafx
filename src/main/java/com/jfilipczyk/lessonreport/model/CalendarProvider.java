package com.jfilipczyk.lessonreport.model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class CalendarProvider {
    
    public InputStream getCalendar(String calendarUrl) throws MalformedURLException, IOException {
        return new URL(calendarUrl).openStream();
    }
}
