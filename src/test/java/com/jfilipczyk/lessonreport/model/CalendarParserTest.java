package com.jfilipczyk.lessonreport.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.Matcher;
import static org.hamcrest.beans.HasPropertyWithValue.hasProperty;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CalendarParserTest {
    
    private CalendarParser parser;
    
    @Before
    public void beforeEach() {
        parser = new CalendarParser();
    }
    
    @Test
    public void it_returns_list_of_correctly_created_events() {
        List<Event> actual = callParser(
                "fixtures/unit/no_recurrence.ics",
                "2016-11-01 00:00:00",
                "2016-11-30 23:59:00"
        );
                
        assertThat(actual.size(), equalTo(2));
        assertThat(actual, hasEvent(
                "Event A",
                "2016-11-07 18:30:00",
                "2016-11-07 19:30:00"
        ));
        assertThat(actual, hasEvent(
                "Event B", 
                "2016-11-08 11:00:00", 
                "2016-11-08 12:00:00"
        ));
    }

    @Test
    public void it_returns_events_only_for_given_date_range() {
        List<Event> actual = callParser(
                "fixtures/unit/no_recurrence.ics",
                "2016-11-01 00:00:00",
                "2016-11-07 23:59:00"
        );

        assertThat(actual.size(), equalTo(1));
        assertThat(actual, hasItemWithStart("2016-11-07 18:30:00"));
    }
    
    @Test
    public void when_events_are_recurring_it_returns_event_for_each_occurence_in_given_date_range() {
        List<Event> actual = callParser(
                "fixtures/unit/with_recurrence.ics",
                "2016-11-01 00:00:00",
                "2016-11-30 23:59:00"
        );

        assertThat(actual.size(), equalTo(4));
        assertThat(actual, hasItemWithStart("2016-11-07 18:30:00"));
        assertThat(actual, hasItemWithStart("2016-11-14 18:30:00"));
        assertThat(actual, hasItemWithStart("2016-11-21 18:30:00"));
        assertThat(actual, hasItemWithStart("2016-11-28 18:30:00"));
    }

    @Test
    public void when_events_are_recurring_it_skips_recurence_exceptions() {
        List<Event> actual = callParser(
                "fixtures/unit/with_recurrence_and_exceptions.ics",
                "2016-11-01 00:00:00",
                "2016-11-30 23:59:00"
        );

        assertThat(actual.size(), equalTo(2));
        assertThat(actual, hasItemWithStart("2016-11-07 18:30:00"));
        assertThat(actual, hasItemWithStart("2016-11-21 18:30:00"));
    }

    private List<Event> callParser(String fixturePath, String dateFromString, String dateToString) {
        InputStream calendarStream = getInputStream(fixturePath);
        Date dateFrom = DateFactory.create(dateFromString);
        Date dateTo = DateFactory.create(dateToString);
        
        try {
            return parser.parse(calendarStream, dateFrom, dateTo);
        } catch (CalendarParserException | IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    private InputStream getInputStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        return classLoader.getResourceAsStream(fileName);
    }
    
    private static Matcher<Iterable<? super Event>> hasItemWithStart(String expectedDate) {
        return hasItem(hasProperty("start", equalTo(DateFactory.create(expectedDate))));
    }
    
    private static Matcher<Iterable<? super Event>> hasEvent(String summary, String startDate, String endDate) {
        return hasItem(EventFactory.create(summary, startDate, endDate));
    }
}
