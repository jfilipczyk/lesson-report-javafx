package com.jfilipczyk.lessonreport.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class GroupedEventTest {

    private final static String NAME = "Some name";
    private final static int TOTAL_TIME = 3600;
    private final static int NUM_OF_EVENTS = 1;

    private GroupedEvent event;

    @Before
    public void beforeEach() {
        event = new GroupedEvent(NAME, TOTAL_TIME, NUM_OF_EVENTS);
    }

    @Test
    public void it_has_name() {
        assertThat(event.getName(), equalTo(NAME));
    }

    @Test
    public void it_has_total_time() {
        assertThat(event.getTotalTime(), equalTo(TOTAL_TIME));
    }
    
    @Test
    public void it_has_num_of_events() {
        assertThat(event.getNumOfEvents(), equalTo(NUM_OF_EVENTS));
    }
    
    @Test
    public void it_can_increase_duration() {
        event.addDuration(5);
        assertThat(event.getTotalTime(), equalTo(TOTAL_TIME + 5));
    }
    
    @Test
    public void it_can_increase_num_of_events() {
        event.incNumOfEvents();
        assertThat(event.getNumOfEvents(), equalTo(NUM_OF_EVENTS + 1));
    }
}
