package com.bharat.snake_and_ladder.model;

public class Snake implements CrossCellElement {

    private final Cell mouth;
    private final Cell tail;

    public Snake(Cell mouth, Cell tail) {
        this.mouth = mouth;
        this.tail = tail;
    }

    @Override
    public Cell startCell() {
        return mouth;
    }

    @Override
    public Cell endCell() {
        return tail;
    }
}
