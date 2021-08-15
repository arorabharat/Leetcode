package com.bharat.snake_and_ladder.model;

public class Ladder implements Jumper {

    private final int base;
    private final int top;

    public Ladder(int base, int top) {
        this.base = base;
        this.top = top;
    }

    @Override
    public int start() {
        return base;
    }

    @Override
    public int end() {
        return top;
    }
}
