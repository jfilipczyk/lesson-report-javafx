package com.jfilipczyk.lessonreport;

import com.jfilipczyk.lessonreport.model.ReportCreator;
import com.jfilipczyk.lessonreport.model.ReportCreatorFactory;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import javafx.concurrent.Task;

public class CreateReportTask extends Task<Void> {
    
    private final String filepath;
    private final String calendarUrl;
    private final Date dateFrom;
    private final Date dateTo;
    
    public CreateReportTask(String filepath, String calendarUrl, LocalDateTime dateFrom, LocalDateTime dateTo) {
        this.filepath = filepath;
        this.calendarUrl = calendarUrl;
        this.dateFrom = convert(dateFrom);
        this.dateTo = convert(dateTo);;
    }
    
    private Date convert(LocalDateTime localDateTime) {
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.systemDefault());
        return Date.from(zonedDateTime.toInstant());
    }
    
    @Override
    protected Void call() throws Exception {
        ReportCreator creator = ReportCreatorFactory.create();
        creator.create(filepath, calendarUrl, dateFrom, dateTo);
        return null;
    }

    @Override
    protected void succeeded() {
        super.succeeded();
        updateMessage("Report saved!");
    }

    @Override
    protected void failed() {
        super.failed();
        updateMessage("Error: " + super.getException().getMessage());
    }
}
