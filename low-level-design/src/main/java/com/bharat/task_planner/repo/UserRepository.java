package com.bharat.task_planner.repo;


import com.bharat.task_planner.exceptions.NoElementFoundException;
import com.bharat.task_planner.model.User;

public interface UserRepository {
    void save(User user);

    void remove(String userId) throws NoElementFoundException;

    User get(String userId) throws NoElementFoundException;
}
