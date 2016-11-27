package com.jfilipczyk.lessonreport.model;

import java.util.Date;

public class Event {
    private final String summary;
    private final Date start;
    private final Date end;

    public Event(String summary, Date start, Date end) {
        this.summary = summary;
        this.start = start;
        this.end = end;
    }

    public String getSummary() {
        return summary;
    }
    
    public int getDuration() {
        return (int)((this.end.getTime() - this.start.getTime()) / 1000); 
    }
}
