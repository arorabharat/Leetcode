package com.bharat.snake_and_ladder.model;

public class Player {

    private final int name;
    private final int id;
    private Cell currentCell;

    public Player(int name,int id,Cell currentCell) {
        this.name = name;
        this.id = id;
        this.currentCell = currentCell;
    }

    public int getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public Cell getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(Cell currentCell) {
        this.currentCell = currentCell;
    }
}
