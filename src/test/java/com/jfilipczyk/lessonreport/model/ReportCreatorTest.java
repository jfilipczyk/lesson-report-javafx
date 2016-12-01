package com.jfilipczyk.lessonreport.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.junit.Test;
import org.junit.Rule;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import static org.mockito.Mockito.*;
import static org.hamcrest.collection.IsIterableContainingInOrder.*;
import static org.junit.Assert.*;

public class ReportCreatorTest {
    
    private static final String FILEPATH = "Some filepath";
    private static final String CALENDAR_URL = "Some URL";
    private static final Date DATE_FROM = DateFactory.create("2016-12-01 00:00:00");
    private static final Date DATE_TO = DateFactory.create("2016-12-31 23:59:59");
    
    @Mock
    private CalendarProvider provider;
    @Mock
    private CalendarParser parser;
    @Mock
    private EventAggregator aggregator;
    @Mock
    private GroupedEventMapper mapper;
    @Mock
    private CSVFileWriter writer;
    
    @InjectMocks
    private ReportCreator creator;
    
    @Rule
    public MockitoRule mockitoRule = MockitoJUnit.rule();
    
    @Captor
    private ArgumentCaptor<List<String[]>> captor;
    
    @Test
    public void it_reads_calendar_from_given_url() throws IOException, CalendarParserException {
        creator.create(FILEPATH, CALENDAR_URL, DATE_FROM, DATE_TO);
        
        verify(provider).getCalendar(CALENDAR_URL);
    }
    
    @Test
    public void it_parses_events_in_given_date_range_from_the_calendar() throws IOException, CalendarParserException {
        InputStream calendarStream = mock(InputStream.class);
        when(provider.getCalendar(any())).thenReturn(calendarStream);
        
        creator.create(FILEPATH, CALENDAR_URL, DATE_FROM, DATE_TO);
        
        verify(parser).parse(calendarStream, DATE_FROM, DATE_TO);
    }
    
    @Test
    public void it_groups_parsed_events() throws IOException, CalendarParserException {
        List<Event> events = mock(List.class);
        when(parser.parse(any(), any(), any())).thenReturn(events);
        
        creator.create(FILEPATH, CALENDAR_URL, DATE_FROM, DATE_TO);
        
        verify(aggregator).aggregate(events);
    }
    
    @Test
    public void it_maps_grouped_events_to_string_values() throws IOException, CalendarParserException {
        int numOfGroupedEvents = 2;
        List<GroupedEvent> groupedEvents = createGroupedEvents(numOfGroupedEvents);
        when(aggregator.aggregate(any())).thenReturn(groupedEvents);
        
        creator.create(FILEPATH, CALENDAR_URL, DATE_FROM, DATE_TO);

        verify(mapper, times(numOfGroupedEvents)).map(any());
        verify(mapper).map(groupedEvents.get(0));
        verify(mapper).map(groupedEvents.get(1));
    }
    
    @Test
    public void it_writes_columns_names_and_then_mapped_string_values_to_csv_file() throws IOException, CalendarParserException {
        int numOfGroupedEvents = 2;
        List<GroupedEvent> groupedEvents = createGroupedEvents(numOfGroupedEvents);
        when(aggregator.aggregate(any())).thenReturn(groupedEvents);
        String[] valuesForFirst = new String[]{"first event value"};
        String[] valuesForSecond = new String[]{"second event value"};
        when(mapper.map(any())).thenReturn(valuesForFirst).thenReturn(valuesForSecond);
        
        String[] columns = new String[]{"some column name"};
        when(mapper.getColumns()).thenReturn(columns);
        
        creator.create(FILEPATH, CALENDAR_URL, DATE_FROM, DATE_TO);
        
        verify(writer).write(any(), captor.capture());
        final List<String[]> values = captor.<List<String[]>>getValue();
        assertThat(values, contains(columns, valuesForFirst, valuesForSecond));
    }
    
    @Test
    public void it_writes_csv_file_to_given_filepath() throws IOException, CalendarParserException {
        creator.create(FILEPATH, CALENDAR_URL, DATE_FROM, DATE_TO);
        verify(writer).write(eq(FILEPATH), any());
    }
    
    private List<GroupedEvent> createGroupedEvents(int size) {
        List<GroupedEvent> groupedEvents = new ArrayList<>();
        for (int i=0; i<size; i++) {
            groupedEvents.add(mock(GroupedEvent.class));
        }
        return groupedEvents;
    }
}
