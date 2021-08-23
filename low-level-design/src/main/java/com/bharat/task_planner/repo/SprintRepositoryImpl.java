package com.bharat.task_planner.repo;

import com.bharat.task_planner.exceptions.NoElementFoundException;
import com.bharat.task_planner.model.Sprint;

import java.util.HashMap;
import java.util.Map;

public class SprintRepositoryImpl implements SprintRepository {

    private final Map<String, Sprint> storage;

    public SprintRepositoryImpl() {
        this.storage = new HashMap<>();
    }

    @Override
    public void save(Sprint sprint) {
        this.storage.put(sprint.getId(), sprint);
    }

    @Override
    public void remove(String sprintId) throws NoElementFoundException {
        if (!this.storage.containsKey(sprintId)) {
            throw new NoElementFoundException("Sprint Id do not exists");
        }
        this.storage.remove(sprintId);
    }

    @Override
    public Sprint get(String sprintId) throws NoElementFoundException {
        Sprint sprint = this.storage.get(sprintId);
        if (sprint == null) {
            throw new NoElementFoundException("Sprint does not exist");
        }
        return sprint;
    }
}
