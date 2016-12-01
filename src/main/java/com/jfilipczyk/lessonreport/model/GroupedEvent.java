package com.jfilipczyk.lessonreport.model;

public class GroupedEvent {
    private final String name;
    private int totalTime;
    private int numOfEvents;

    public GroupedEvent(String name, int totalTime, int numOfEvents) {
        this.name = name;
        this.totalTime = totalTime;
        this.numOfEvents = numOfEvents;
    }

    public String getName() {
        return name;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public int getNumOfEvents() {
        return numOfEvents;
    }
    
    public void addDuration(int duration) {
        this.totalTime += duration;
    }
    
    public void incNumOfEvents() {
        this.numOfEvents++;
    }
}
