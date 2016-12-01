package com.jfilipczyk.lessonreport.model;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.junit.runner.RunWith;

@RunWith(DataProviderRunner.class)
public class EventTest {
    
    private final static String SUMMARY = "Some summary";
    private final static Date START_DATE = DateFactory.create("2016-12-01 12:00:00");
    private final static Date END_DATE = DateFactory.create("2016-12-01 13:30:00");
    private final static Date NOT_EQUAL_DATE = DateFactory.create("2016-01-01 10:00:00");
    private final static String NOT_EQUAL_SUMMARY = "Other summary";
    
    @Test
    public void it_has_summary() {
        Event event = new Event(SUMMARY, START_DATE, END_DATE);
        assertThat(event.getSummary(), equalTo(SUMMARY));
    }
    
    @Test
    public void it_has_immutable_start() {
        Event event = new Event(SUMMARY, START_DATE, END_DATE);
        assertThat(event.getStart(), equalTo(START_DATE));
        assertThat(event.getStart(), not(sameInstance(START_DATE)));

        event.getStart().setTime(NOT_EQUAL_DATE.getTime());
        assertThat(event.getStart(), equalTo(START_DATE));
    }
    
    @Test
    public void it_has_immutable_end() {
        Event event = new Event(SUMMARY, START_DATE, END_DATE);
        assertThat(event.getEnd(), equalTo(END_DATE));
        assertThat(event.getEnd(), not(sameInstance(END_DATE)));

        event.getEnd().setTime(NOT_EQUAL_DATE.getTime());
        assertThat(event.getEnd(), equalTo(END_DATE));
    }
    
    @Test
    public void it_calculates_duration_in_seconds() {
        Event event = new Event(SUMMARY, START_DATE, END_DATE);
        assertThat(event.getDuration(), equalTo((int) (1.5 * 3600)));
    }
    
    @DataProvider
    public static Object[][] checkEqualityProvider() {
        return new Object[][]{
            {new Event(SUMMARY, START_DATE, END_DATE), true},
            {new Event(NOT_EQUAL_SUMMARY, START_DATE, END_DATE), false},
            {new Event(SUMMARY, NOT_EQUAL_DATE, END_DATE), false},
            {new Event(SUMMARY, START_DATE, NOT_EQUAL_DATE), false}
        };
    }
    
    @Test
    @UseDataProvider("checkEqualityProvider")
    public void it_checks_equality_by_value(Event otherEvent, boolean isEqual) {
        Event event = new Event(SUMMARY, START_DATE, END_DATE);
        
        if (isEqual) {
            assertThat(event, equalTo(otherEvent));
            assertThat(event.hashCode(), equalTo(otherEvent.hashCode()));
        } else {
            assertThat(event, not(equalTo(otherEvent)));
        }
    }
}
