package com.bharat.task_planner.model;


import java.util.ArrayList;
import java.util.List;

public class Sprint {

    private final String id;
    private final String name;
    List<Task> tasks;
    private final User creator;

    public Sprint(String id, String name, User creator) {
        this.id = id;
        this.name = name;
        this.tasks = new ArrayList<>();
        this.creator = creator;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    @Override
    public String toString() {
        return "Sprint{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", tasks=" + tasks +
                '}';
    }
}
