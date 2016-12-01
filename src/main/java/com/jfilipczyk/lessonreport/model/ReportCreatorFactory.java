package com.jfilipczyk.lessonreport.model;

public class ReportCreatorFactory {

    public static ReportCreator create() {
        return new ReportCreator(
            new CalendarProvider(),
            new CalendarParser(),
            new EventAggregator(),
            new GroupedEventMapper(),
            new CSVFileWriter()
        );
    }
}
