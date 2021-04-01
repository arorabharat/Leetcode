package tic_tac_toe;

import java.util.Random;

public class TurnManager {

    private int turn;

    private final int numOfPlayers;

    public TurnManager(int numOfPlayers) {
        Random random = new Random();
        this.numOfPlayers = numOfPlayers;
        this.turn = random.nextInt(numOfPlayers);
    }

    public int getCurrentTurn() {
        return this.turn;
    }

    public int getNextTurn() {
        turn = (turn + 1) % this.numOfPlayers;
        return turn;
    }
}
