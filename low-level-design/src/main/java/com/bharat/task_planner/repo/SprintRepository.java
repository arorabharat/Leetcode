package com.bharat.task_planner.repo;


import com.bharat.task_planner.exceptions.NoElementFoundException;
import com.bharat.task_planner.model.Sprint;

public interface SprintRepository {
    void save(Sprint sprint);

    void remove(String sprintId) throws NoElementFoundException;

    Sprint get(String sprintId) throws NoElementFoundException;
}
