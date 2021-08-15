package com.bharat.snake_and_ladder.model;

public interface TurnManager {

    int currentTurn();
    void setCurrentTurn(int turn);
    int nextTurn();
    int getNumberOfPlayers();
    int setNumberOfPlayers(int numberOfPlayers);
}
