package com.bharat.task_planner.repo;

import com.bharat.exceptions.NoElementFoundException;
import com.bharat.model.User;

import java.util.HashMap;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {

    private final Map<String, User> storage;

    public UserRepositoryImpl() {
        this.storage = new HashMap<>();
    }

    @Override
    public void save(User user) {
        this.storage.put(user.getId(), user);
    }

    @Override
    public void remove(String userId) throws NoElementFoundException {
        if (!this.storage.containsKey(userId)) {
            throw new NoElementFoundException("User Id do not exists");
        }
        this.storage.remove(userId);
    }

    @Override
    public User get(String userId) throws NoElementFoundException {
        User user = this.storage.get(userId);
        if (user == null) {
            throw new NoElementFoundException("User does not exist");
        }
        return user;
    }
}
