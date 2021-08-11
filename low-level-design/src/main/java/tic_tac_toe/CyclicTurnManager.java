package tic_tac_toe;

import java.util.Random;

public class CyclicTurnManager implements TurnManager {

    private final int totalTurns;
    private int currentTurn;

    public CyclicTurnManager(int totalTurns) {
        Random random = new Random();
        this.totalTurns = totalTurns;
        this.currentTurn = random.nextInt(totalTurns);
    }

    @Override
    public int getNextTurn() {
        currentTurn = (currentTurn + 1) % this.totalTurns;
        return currentTurn;
    }

    @Override
    public int currentTurn() {
        return this.currentTurn;
    }

    @Override
    public int totalTurns() {
        return this.totalTurns;
    }

    @Override
    public int setFirstTurn(int turn) {
        return this.currentTurn = turn;
    }
}
