package com.bharat.task_planner.controller;


import com.bharat.task_planner.constants.FeatureImpactStatus;
import com.bharat.task_planner.constants.SubTrackStatus;
import com.bharat.task_planner.constants.TaskType;
import com.bharat.task_planner.exceptions.NoElementFoundException;
import com.bharat.task_planner.model.*;
import com.bharat.task_planner.repo.TaskRepository;
import com.bharat.task_planner.repo.UserRepository;
import com.bharat.task_planner.utils.IDGenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskController {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final IDGenerator idGenerator;
    private final TaskFactory taskFactory;
    private final Map<String, List<Task>> userToTaskMap;

    public TaskController(TaskRepository taskRepository, UserRepository userRepository, IDGenerator idGenerator, TaskFactory taskFactory) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.idGenerator = idGenerator;
        this.taskFactory = taskFactory;
        this.userToTaskMap = new HashMap<>();
    }

    public String createTask(String title, String creatorId, TaskStatus status, TaskType type, long dueDate) {
        String taskId = idGenerator.getId();
        try {
            User creator = this.userRepository.get(creatorId);
            Task task = taskFactory.createTaskOfType(title, creator, status, type, dueDate, taskId);
            this.taskRepository.save(task);
            return taskId;
        } catch (NoElementFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public int addFeatureSummary(String taskId, String summary, FeatureImpactStatus impactStatus) {
        try {
            Task task = taskRepository.get(taskId);
            if (!(task instanceof Feature)) {
                // throw exception
                return 400;
            } else {
                Feature feature = (Feature) task;
                feature.setSummary(summary);
                feature.setFeatureImpactStatus(impactStatus);
                return 200;
            }
        } catch (NoElementFoundException e) {
            e.printStackTrace();
            return 400;
        }
    }

    public int addStorySummary(String taskId, String summary) {
        try {
            Task task = taskRepository.get(taskId);
            if (!(task instanceof Story)) {
                // throw exception
                return 400;
            } else {
                Story story = (Story) task;
                story.setSummary(summary);
                return 200;
            }
        } catch (NoElementFoundException e) {
            e.printStackTrace();
            return 400;
        }
    }

    public int addStorySubTrack(String taskId, String title, SubTrackStatus status) {
        try {
            Task task = taskRepository.get(taskId);
            if (!(task instanceof Story)) {
                // throw exception
                return 400;
            } else {
                Story story = (Story) task;
                String id = idGenerator.getId();
                story.addSubTrack(new SubTrack(title, status, id));
                return 200;
            }
        } catch (NoElementFoundException e) {
            e.printStackTrace();
            return 400;
        }
    }

    public int setAssigneeForTask(String taskId, String userId) {
        try {
            Task task = taskRepository.get(taskId);
            User user = this.userRepository.get(userId);
            task.setAssignee(user);
            if (!this.userToTaskMap.containsKey(userId)) {
                this.userToTaskMap.put(userId, new ArrayList<>());
            }
            this.userToTaskMap.get(userId).add(task);
            return 200;
        } catch (NoElementFoundException e) {
            e.printStackTrace();
            return 400;
        }
    }

    public int setStatusForTask(String taskId, TaskStatus taskStatus) {
        try {
            Task task = taskRepository.get(taskId);
            task.setStatus(taskStatus);
            return 200;
        } catch (NoElementFoundException e) {
            e.printStackTrace();
            return 400;
        }
    }

    public void displayTaskAssignedToUser(String userId) {
        User user = null;
        try {
            user = this.userRepository.get(userId);
            List<Task> taskAssignedToUser = this.userToTaskMap.getOrDefault(user.getId(), new ArrayList<>());
            System.out.println(user);
            for (Task task : taskAssignedToUser) {
                System.out.println(task);
            }
            System.out.println();
        } catch (NoElementFoundException e) {
            e.printStackTrace();
        }

    }
}
