package com.jfilipczyk.lessonreport.model;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class ParserTest {
    
    private Parser parser;
    
    @Before
    public void beforeEach() {
        parser = new Parser();
    }
    
    @Test
    public void test_returns_list_of_events() throws IOException, ParseException {
        String calendarContent = getFile("fixtures/no_recurrence.ics");
        Date dateFrom = createDate("2016-11-01");
        Date dateTo = createDate("2016-11-30");
        
        List<Event> actual = parser.parse(calendarContent, dateFrom, dateTo);
        
        assertEquals(1, actual.size());
    }
    
    private String getFile(String fileName) throws IOException {
        ClassLoader classLoader = getClass().getClassLoader();
        return IOUtils.toString(classLoader.getResourceAsStream(fileName));
    }
    
    private Date createDate(String dateString) throws ParseException {
        return new SimpleDateFormat("Y-M-d").parse(dateString);
    }
    
}
