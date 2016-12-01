package com.jfilipczyk.lessonreport.model;

import java.util.Date;
import java.util.Objects;

public final class Event {
    private final String summary;
    private final Date start;
    private final Date end;

    public Event(String summary, Date start, Date end) {
        this.summary = summary;
        this.start = new Date(start.getTime());
        this.end = new Date(end.getTime());
    }

    public String getSummary() {
        return summary;
    }
    
    public int getDuration() {
        return (int)((this.end.getTime() - this.start.getTime()) / 1000); 
    }

    public Date getStart() {
        return new Date(start.getTime());
    }

    public Date getEnd() {
        return new Date(end.getTime());
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Event other = (Event) obj;
        if (!Objects.equals(this.summary, other.summary)) {
            return false;
        }
        if (!Objects.equals(this.start, other.start)) {
            return false;
        }
        if (!Objects.equals(this.end, other.end)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.summary);
        hash = 41 * hash + Objects.hashCode(this.start);
        hash = 41 * hash + Objects.hashCode(this.end);
        return hash;
    }
}
