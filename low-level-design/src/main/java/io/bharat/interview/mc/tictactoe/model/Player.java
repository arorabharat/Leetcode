package io.bharat.interview.mc.tictactoe.model;

public class Player {

    private final Symbol symbol;
    private final int id;

    public Player(Symbol symbol, int id) {
        this.symbol = symbol;
        this.id = id;
    }

    public Symbol getSymbol() {
        return symbol;
    }

    public int getId() {
        return id;
    }
}
