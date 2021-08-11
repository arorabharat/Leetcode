package tic_tac_toe;

public interface TurnManager {

    int getNextTurn();

    int currentTurn();

    int totalTurns();

    int setFirstTurn(int turn);
}
