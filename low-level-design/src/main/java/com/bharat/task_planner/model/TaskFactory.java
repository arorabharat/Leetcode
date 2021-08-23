package com.bharat.task_planner.model;


import com.bharat.task_planner.constants.TaskType;

public class TaskFactory {

    public Task createTaskOfType(String title, User creator, TaskStatus status, TaskType type, long dueDate, String id) {
        switch (type) {
            case STORY -> {
                return new Story(title, creator, status, dueDate, id);
            }
            case FEATURE -> {
                return new Feature(title, creator, status, dueDate, id);
            }
            default -> {
                throw new IllegalArgumentException("Type not exist");
            }
        }
    }
}
