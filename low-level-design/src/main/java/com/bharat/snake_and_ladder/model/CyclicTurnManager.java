package com.bharat.snake_and_ladder.model;

public class CyclicTurnManager implements TurnManager {

    private int numberOfPlayers;
    private int currentTurn;

    CyclicTurnManager(int numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
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
        return currentTurn % numberOfPlayers;
    }

    @Override
    public int getNumberOfPlayers() {
        return numberOfPlayers;
    }

    @Override
    public int setNumberOfPlayers(int numberOfPlayers) {
        return this.numberOfPlayers = numberOfPlayers;
    }
}
