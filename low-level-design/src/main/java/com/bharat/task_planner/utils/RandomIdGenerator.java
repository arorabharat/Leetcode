package com.bharat.task_planner.utils;

import java.util.UUID;

public class RandomIdGenerator implements IDGenerator {

    @Override
    public String getId() {
        return UUID.randomUUID().toString();
    }
}
