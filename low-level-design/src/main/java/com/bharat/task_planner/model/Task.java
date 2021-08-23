package com.bharat.task_planner.model;

public abstract class Task {

    private final String title;
    private final User creatorId;
    private final long dueDate;
    private final String id;
    private TaskStatus status;
    private User assignee;


    public Task(String title, User creatorId, TaskStatus status, long dueDate, String id) {
        this.title = title;
        this.creatorId = creatorId;
        this.status = status;
        this.dueDate = dueDate;
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public User getCreator() {
        return creatorId;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public long getDueDate() {
        return dueDate;
    }

    public String getId() {
        return id;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", creatorId=" + creatorId +
                ", status=" + status +
                ", dueDate=" + dueDate +
                ", id='" + id + '\'' +
                ", assignee=" + assignee +
                '}';
    }
}
