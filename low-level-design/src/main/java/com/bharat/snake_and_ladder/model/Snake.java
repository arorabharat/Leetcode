package com.bharat.snake_and_ladder.model;

public class Snake implements Jumper {

    private final int mouth;
    private final int tail;

    public Snake(int mouth, int tail) {
        this.mouth = mouth;
        this.tail = tail;
    }

    @Override
    public int getStart() {
        return mouth;
    }

    @Override
    public int getEnd() {
        return tail;
    }

    @Override
    public String toString() {
        return "Snake{" +
                "mouth=" + mouth +
                ", tail=" + tail +
                '}';
    }
}
