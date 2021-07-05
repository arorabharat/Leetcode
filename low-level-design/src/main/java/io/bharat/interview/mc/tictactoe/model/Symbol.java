package io.bharat.interview.mc.tictactoe.model;

public enum Symbol {

    CROSS("X"),
    CIRCLE("O"),
    EMPTY(" ");

    String value;

    Symbol(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
