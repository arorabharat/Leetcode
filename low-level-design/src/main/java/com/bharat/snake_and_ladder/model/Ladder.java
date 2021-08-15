package com.bharat.snake_and_ladder.model;

public class Ladder implements Jumper {

    private final int base;
    private final int top;

    public Ladder(int base, int top) {
        this.base = base;
        this.top = top;
    }

    @Override
    public int getStart() {
        return base;
    }

    @Override
    public int getEnd() {
        return top;
    }

    @Override
    public String toString() {
        return "Ladder{" +
                "base=" + base +
                ", top=" + top +
                '}';
    }
}
