package com.jfilipczyk.lessonreport.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventAggregator {
       
    public List<GroupedEvent> aggregate(List<Event> events) {
        HashMap<String, GroupedEvent> groupedEventsMap = events.stream()
            .collect(
                () -> new HashMap<>(), 
                (map, event) -> {
                    String groupName = extractGroupName(event.getSummary());
                    if (map.containsKey(groupName)) {
                        GroupedEvent groupedEvent = map.get(groupName);
                        groupedEvent.addDuration(event.getDuration());
                        groupedEvent.incNumOfEvents();
                    } else {
                        GroupedEvent groupedEvent = new GroupedEvent(groupName, event.getDuration(), 1);
                        map.put(groupName, groupedEvent);
                    }
                },
                (mapA, mapB) -> {}); // not required for sequential stream
        
        return new ArrayList<>(groupedEventsMap.values());
    }
    
    private String extractGroupName(String name) {
        return name.split("#")[0];
    }
}
