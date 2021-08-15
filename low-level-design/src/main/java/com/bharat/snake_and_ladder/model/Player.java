package com.bharat.snake_and_ladder.model;

public class Player {

    private final String name;
    private int currentCell;

    public Player(String name) {
        this.name = name;
        this.currentCell = 0;
    }

    public String getName() {
        return name;
    }

    public int getCurrentCell() {
        return currentCell;
    }

    public void setCurrentCell(int currentCell) {
        this.currentCell = currentCell;
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", currentCell=" + currentCell +
                '}';
    }
}
