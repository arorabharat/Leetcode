package io.bharat.interview.mc.tictactoe.model;

public interface TicTacToe {
    boolean isTimed();

    boolean isGameFinished();

    void move(Move move);
}
