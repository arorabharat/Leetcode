package com.bharat.snake_and_ladder.driver;

import com.bharat.snake_and_ladder.model.*;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        List<Jumper> jumpers = new ArrayList<>();

        Jumper ladder1 = new Ladder(11, 98);
        Jumper ladder2 = new Ladder(2, 30);
        Jumper snake1 = new Snake(30, 10);
        jumpers.add(ladder1);
        jumpers.add(ladder2);
        jumpers.add(snake1);

        Board board = Board.BoardBuilder.newInstance()
                .setSize(100)
                .setJumpers(jumpers)
                .buildBoard();

        List<Player> players = new ArrayList<>();

        Player player1 = new Player("Alice");
        Player player2 = new Player("Bob");
        players.add(player1);
        players.add(player2);

        List<Dice> dices = new ArrayList<>();
        Dice dice1 = new RandomNumberDice(1, 6);
        dices.add(dice1);

        Game game = Game.GameBuilder.newInstance()
                .setBoard(board)
                .setPlayers(players)
                .setTurnManager(new CyclicTurnManager(players.size()))
                .setDices(dices)
                .buildGame();

        game.startGame();

    }
}
