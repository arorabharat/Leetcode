package com.bharat.task_planner.model;

import java.util.ArrayList;
import java.util.List;

public class Story extends Task {

    private final List<SubTrack> subTrackList;
    private String summary;

    public Story(String title, User creator, TaskStatus status, long dueDate, String id) {
        super(title, creator, status, dueDate, id);
        this.subTrackList = new ArrayList<>();
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public void addSubTrack(SubTrack subTrack) {
        this.subTrackList.add(subTrack);
    }


    @Override
    public String toString() {
        return "Story{" +
                "subTrackList=" + subTrackList +
                ", summary='" + summary + '\'' +
                '}';
    }
}
