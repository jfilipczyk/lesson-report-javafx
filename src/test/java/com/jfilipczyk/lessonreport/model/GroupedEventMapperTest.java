package com.jfilipczyk.lessonreport.model;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.collection.IsArrayContainingInOrder.*;

public class GroupedEventMapperTest {
        
    private static final int HOUR = 3600;
    private GroupedEventMapper mapper;
    
    @Before
    public void beforeEach() {
        mapper = new GroupedEventMapper();
    }

    @Test
    public void it_has_columns_definition() {
        assertThat(mapper.getColumns(), is(arrayContaining("Name", "Num of events", "Total time [h]")));
    }
    
    @Test
    public void it_maps_event() {
        String name = "Some name";
        int numOfEvents = 2;
        int totalTime = (int) (HOUR * 1.5);
        GroupedEvent event = new GroupedEvent(name, totalTime, numOfEvents);
        
        String[] actualResult = mapper.map(event);
        
        assertThat(actualResult, is(arrayContaining(name, "2", "1.50")));
    }
}
