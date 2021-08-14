package com.bharat.snake_and_ladder.model;

import java.util.ArrayList;
import java.util.List;

/**
 * https://workat.tech/machine-coding/editorial/how-to-design-snake-and-ladder-machine-coding-ehskk9c40x2w
 */
public class Game {

    private Board board;
    List<Player> players;
    TurnManager turnManager;

    public Game(Board board,TurnManager turnManager) {
        this.board = board;
        this.players = new ArrayList<>();
        this.turnManager = turnManager;
    }

    void addPlayer(Player player) {
        this.players.add(player);
    }


}
