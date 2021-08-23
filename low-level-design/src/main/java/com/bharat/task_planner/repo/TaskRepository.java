package com.bharat.task_planner.repo;


import com.bharat.task_planner.exceptions.NoElementFoundException;
import com.bharat.task_planner.model.Task;

public interface TaskRepository {
    void save(Task task);

    void remove(String taskId) throws NoElementFoundException;

    Task get(String taskId) throws NoElementFoundException;
}
