package com.jfilipczyk.lessonreport.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportCreator {

    private final CalendarProvider provider;
    private final CalendarParser parser;
    private final EventAggregator aggregator;
    private final GroupedEventMapper mapper;
    private final CSVFileWriter writer;

    public ReportCreator(CalendarProvider provider, CalendarParser parser, EventAggregator aggregator, GroupedEventMapper mapper, CSVFileWriter writer) {
        this.provider = provider;
        this.parser = parser;
        this.aggregator = aggregator;
        this.mapper = mapper;
        this.writer = writer;
    }
    
    public void create(String filepath, String calendarUrl, Date dateFrom, Date dateTo) throws IOException, CalendarParserException {
        InputStream calendarStream = provider.getCalendar(calendarUrl);
        List<Event> events = parser.parse(calendarStream, dateFrom, dateTo);
        
        List<String[]> values = new ArrayList<>();
        values.add(mapper.getColumns());
               
        aggregator.aggregate(events).forEach(groupedEvent -> {
            values.add(mapper.map(groupedEvent));
        });
                
        writer.write(filepath, values);
    }
}
