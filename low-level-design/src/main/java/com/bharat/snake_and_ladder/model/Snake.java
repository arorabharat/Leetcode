package com.bharat.snake_and_ladder.model;

public class Snake implements Jumper {

    private final Cell mouth;
    private final Cell tail;

    public Snake(Cell mouth, Cell tail) {
        this.mouth = mouth;
        this.tail = tail;
    }

    @Override
    public Cell start() {
        return mouth;
    }

    @Override
    public Cell end() {
        return tail;
    }
}
