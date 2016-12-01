package com.jfilipczyk.lessonreport.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.fortuna.ical4j.data.CalendarBuilder;
import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.Component;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Period;
import net.fortuna.ical4j.model.Property;

public class CalendarParser {
    
    public List<Event> parse(InputStream calendarStream, Date dateFrom, Date dateTo) throws IOException, CalendarParserException {
        final Calendar calendar = createCalendar(calendarStream);
        final Period parsePeriod = new Period(new DateTime(dateFrom), new DateTime(dateTo));
        final List<Event> events = new ArrayList<>();
        
        List<Component> components = (List<Component>) calendar.getComponents();
        return components.parallelStream()
            .filter(c -> c.getName().equals(Component.VEVENT))
            .flatMap(c -> createEventStream(c, parsePeriod))
            .collect(Collectors.toList());
    }

    private Calendar createCalendar(InputStream calendarStream) throws IOException, CalendarParserException {
        try {
            return new CalendarBuilder().build(calendarStream);
        } catch (net.fortuna.ical4j.data.ParserException e) {
            throw new CalendarParserException(e);
        }
        
    }

    private Stream<Event> createEventStream(Component component, Period parsePeriod) {
        String summary = component.getProperty(Property.SUMMARY).getValue();
        return component.calculateRecurrenceSet(parsePeriod).parallelStream()
            .map(p -> {
                Period period = (Period) p;
                return new Event(summary, period.getStart(), period.getEnd());
            });
    }
}
