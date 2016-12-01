package com.jfilipczyk.lessonreport.model;

import java.util.ArrayList;
import java.util.List;
import static org.hamcrest.CoreMatchers.*;
import org.hamcrest.Matcher;
import static org.hamcrest.beans.HasPropertyWithValue.*;
import static org.junit.Assert.assertThat;
import org.junit.Before;
import org.junit.Test;

public class EventAggregatorTest {
    
    private List<Event> events;
    
    @Before
    public void beforeEach() {
        events = new ArrayList<>();
    }
    
    @Test
    public void it_groups_events_by_summary_begining() {
        addEvent("group A# not important details");
        addEvent("group B# not important details");
        addEvent("group A# anything");
        addEvent("group C");
        
        List<GroupedEvent> actual = callAggregator();
        
        assertThat(actual.size(), equalTo(3));
        assertThat(actual, hasItemWithName("group A"));
        assertThat(actual, hasItemWithName("group B"));
        assertThat(actual, hasItemWithName("group C"));
    }

    @Test
    public void it_calculates_numOfEvents_for_group() {
        addEvent("group A");
        addEvent("group A");
        
        List<GroupedEvent> actual = callAggregator();

        assertThat(actual, hasItem(hasProperty("numOfEvents", equalTo(2))));
    }
    
    @Test
    public void it_calculates_totalTime_in_seconds_for_group() {
        addEvent("group A", 100);
        addEvent("group A", 200);

        List<GroupedEvent> actual = callAggregator();

        assertThat(actual, hasItem(hasProperty("totalTime", equalTo(300))));
    }

    private List<GroupedEvent> callAggregator() {
        return new EventAggregator().aggregate(events);
    }
    
    private void addEvent(String summary) {
        events.add(EventFactory.create(summary));
    }
    
    private void addEvent(String summary, int duration) {
        events.add(EventFactory.create(summary, duration));
    }
    
    private static Matcher<Iterable<? super GroupedEvent>> hasItemWithName(String expectedName) {
        return hasItem(hasProperty("name", equalTo(expectedName)));
    }
}
