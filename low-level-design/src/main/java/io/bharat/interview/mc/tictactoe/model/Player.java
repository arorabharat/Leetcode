package io.bharat.interview.mc.tictactoe.model;

public class Player {

    private final Symbol symbol;
    private final int id;
    private String name;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Player{" +
                "symbol=" + symbol +
                ", name='" + name + '\'' +
                ", id=" + (id + 1) +
                '}';
    }
}
