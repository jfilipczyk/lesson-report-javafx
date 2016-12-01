package com.jfilipczyk.lessonreport.model;

import java.util.Date;

public class EventFactory {

    private static final Date DEFAULT_START_DATE = DateFactory.create("2016-11-01 18:00:00");
    private static final Date DEFAULT_END_DATE = DateFactory.create("2016-11-01 19:00:00");
    
    public static Event create(String summary, String startDate, String endDate) {
        return new Event(summary, DateFactory.create(startDate), DateFactory.create(endDate));
    }
    
    public static Event create(String summary) {
        return new Event(summary, DEFAULT_START_DATE, DEFAULT_END_DATE);
    }
    
    public static Event create(String summary, int durationInSec) {
        Date endDate = new Date(DEFAULT_START_DATE.getTime() + durationInSec * 1000);
        return new Event(summary, DEFAULT_START_DATE, endDate);
    }
}
