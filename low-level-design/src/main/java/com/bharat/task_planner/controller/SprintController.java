package com.bharat.task_planner.controller;

import com.bharat.task_planner.exceptions.NoElementFoundException;
import com.bharat.task_planner.model.Sprint;
import com.bharat.task_planner.model.Task;
import com.bharat.task_planner.model.User;
import com.bharat.task_planner.repo.SprintRepository;
import com.bharat.task_planner.repo.TaskRepository;
import com.bharat.task_planner.repo.UserRepository;
import com.bharat.task_planner.utils.IDGenerator;

public class SprintController {

    private final SprintRepository sprintRepository;
    private final IDGenerator idGenerator;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    public SprintController(SprintRepository sprintRepository, IDGenerator idGenerator, UserRepository userRepository, TaskRepository taskRepository) {
        this.sprintRepository = sprintRepository;
        this.idGenerator = idGenerator;
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    public String createSprint(String sprintName, String userId) {
        String sprintId = idGenerator.getId();
        try {
            User user = this.userRepository.get(userId);
            Sprint sprint = new Sprint(sprintId, sprintName, user);
            sprintRepository.save(sprint); // can use try catch block to return right status code with check exception
            return sprintId;
        } catch (NoElementFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int deleteSprint(String sprintId) {
        try {
            sprintRepository.remove(sprintId);
            return 200;
        } catch (NoElementFoundException e) {
            e.printStackTrace();
            return 400;
        }
    }

    public int addTask(String sprintId, String taskId) {
        try {
            Task task = this.taskRepository.get(taskId);
            Sprint sprint = sprintRepository.get(sprintId);
            sprint.addTask(task);
            return 200;
        } catch (NoElementFoundException e) {
            e.printStackTrace();
            return 400;
        }
    }

    public int displaySprintSnapShot(String sprintId) {
        try {
            // current time : task past current time + future task;
            Sprint sprint = sprintRepository.get(sprintId);
            System.out.println(sprint);
            return 200;
        } catch (NoElementFoundException e) {
            e.printStackTrace(); // can print uuid for better tracing
            return 400;
        }
    }
}
