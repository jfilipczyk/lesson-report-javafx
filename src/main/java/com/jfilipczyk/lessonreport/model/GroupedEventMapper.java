package com.jfilipczyk.lessonreport.model;

import java.text.DecimalFormat;

public class GroupedEventMapper {

    private static final String[] COLUMNS = {"Name", "Num of events", "Total time [h]"};
    
    public String[] getColumns() {
        return COLUMNS;
    }
    
    public String[] map(GroupedEvent event) {
        String[] values = new String[3];
        values[0] = event.getName();
        values[1] = String.valueOf(event.getNumOfEvents());
        values[2] = (new DecimalFormat("0.00").format(getTotalTimeInHours(event)));
        return values;
    }

    private static double getTotalTimeInHours(GroupedEvent event) {
        return (double) event.getTotalTime() / 3600;
    }
}
