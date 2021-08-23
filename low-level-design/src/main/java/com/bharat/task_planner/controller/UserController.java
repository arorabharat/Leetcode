package com.bharat.task_planner.controller;

import com.bharat.model.User;
import com.bharat.repo.UserRepository;
import com.bharat.utils.IDGenerator;

public class UserController {

    private final UserRepository userRepository;
    private final IDGenerator idGenerator;

    public UserController(UserRepository userRepository, IDGenerator idGenerator) {
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
    }

    public String addUser(String userName) {
        String id = idGenerator.getId();
        this.userRepository.save(new User(id, userName));
        return id;
    }

    public int removeUser(String userId) {
        // to remove user
        return 200;
    }
}
