package com.bharat.snake_and_ladder.model;

public class Ladder implements CrossCellElement {

    private final Cell base;
    private final Cell top;

    public Ladder(Cell base, Cell top) {
        this.base = base;
        this.top = top;
    }

    @Override
    public Cell startCell() {
        return base;
    }

    @Override
    public Cell endCell() {
        return top;
    }
}
