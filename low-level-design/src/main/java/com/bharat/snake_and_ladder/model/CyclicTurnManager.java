package com.bharat.snake_and_ladder.model;

public class CyclicTurnManager implements TurnManager {

    private final int numOfPlayers;
    private int currentTurn;

    CyclicTurnManager(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
    }

    @Override
    public int currentTurn() {
        return currentTurn;
    }

    @Override
    public void setCurrentTurn(int turn) {
        currentTurn = turn;
    }

    @Override
    public int nextTurn() {
        currentTurn++;
        return currentTurn % numOfPlayers;
    }

    @Override
    public int numberOfPlayers() {
        return numOfPlayers;
    }
}
