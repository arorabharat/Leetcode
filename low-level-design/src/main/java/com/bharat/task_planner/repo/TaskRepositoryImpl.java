package com.bharat.task_planner.repo;

import com.bharat.exceptions.NoElementFoundException;
import com.bharat.model.Task;

import java.util.HashMap;
import java.util.Map;

public class TaskRepositoryImpl implements TaskRepository {

    private final Map<String, Task> storage;

    public TaskRepositoryImpl() {
        this.storage = new HashMap<>();
    }

    @Override
    public void save(Task task) {
        this.storage.put(task.getId(), task);
    }

    @Override
    public void remove(String sprintId) throws NoElementFoundException {
        if (!this.storage.containsKey(sprintId)) {
            throw new NoElementFoundException("Sprint Id do not exists");
        }
        this.storage.remove(sprintId);
    }

    @Override
    public Task get(String taskId) throws NoElementFoundException {
        Task task = this.storage.get(taskId);
        if (task == null) {
            throw new NoElementFoundException("Sprint does not exist");
        }
        return task;
    }
}
