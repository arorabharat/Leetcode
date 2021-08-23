package com.bharat.task_planner.model;


import com.bharat.task_planner.constants.SubTrackStatus;

public class SubTrack {

    private final String title;
    private final SubTrackStatus status;
    private final String id;

    public SubTrack(String title, SubTrackStatus status, String id) {
        this.title = title;
        this.status = status;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public SubTrackStatus getStatus() {
        return status;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "SubTrack{" +
                "title='" + title + '\'' +
                ", status=" + status +
                ", id='" + id + '\'' +
                '}';
    }
}
