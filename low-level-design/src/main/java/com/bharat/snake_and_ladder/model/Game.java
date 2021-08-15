package com.bharat.snake_and_ladder.model;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

/**
 * https://workat.tech/machine-coding/editorial/how-to-design-snake-and-ladder-machine-coding-ehskk9c40x2w
 * <p>
 * <p>
 * Methods
 */
public class Game {

    private final Scanner scanner = new Scanner(System.in);
    private Board board;
    private TurnManager turnManager;
    private List<Player> players;
    private List<Dice> dices;

    private Game() {
    }

    // game will finish if there is only one player left
    public boolean isGameFinished() {
        int leftPLayers = 0;
        for (Player player : players) {
            if (board.getLastCell() != player.getCurrentCell()) {
                leftPLayers++;
            }
        }
        return leftPLayers > 1;
    }

    public void startGame() {
        // move all player to starting point
        for (Player player : players) {
            player.setCurrentCell(board.getStartCell());
        }
        while (!this.isGameFinished()) {

            System.out.println("Press 1 to continue and  2to quit");
            int input = scanner.nextInt();
            if (input == 2) {
                System.out.println();
            }

            // skip the player if player has already won
            int currentTurn = turnManager.nextTurn();
            Player currentPlayer = players.get(currentTurn);
            while (board.getLastCell() == currentPlayer.getCurrentCell()) {
                currentTurn = turnManager.nextTurn();
                currentPlayer = players.get(currentTurn);
            }
            System.out.println(currentPlayer);

            // roll all the dice and take the total count
            int totalDiceNumber = 0;
            for (Dice dice : dices) {
                totalDiceNumber = totalDiceNumber + dice.rollTheDice();
            }
            System.out.println(currentPlayer.getName() + " got " + totalDiceNumber + " dices");

            // set the final cell of the current player
            int finalCell = board.moveAheadNStep(currentPlayer.getCurrentCell(), totalDiceNumber);
            currentPlayer.setCurrentCell(finalCell);
            System.out.println("Moving " + currentPlayer.getName() + " to cell " + finalCell);

            // if player has won the game print
            if (board.getLastCell() == currentPlayer.getCurrentCell()) {
                System.out.println(currentPlayer.getName() + " has won the game");
            }
        }
        System.out.println("Game is finished");
    }


    public static class GameBuilder {

        private final Game game;

        GameBuilder() {
            game = new Game();
        }

        public static GameBuilder newInstance() {
            return new GameBuilder();
        }

        public GameBuilder setBoard(Board board) {
            this.game.board = board;
            return this;
        }

        public GameBuilder setPlayers(List<Player> player) {
            this.game.players = player;
            return this;
        }

        public GameBuilder setDices(List<Dice> dices) {
            this.game.dices = dices;
            return this;
        }

        public GameBuilder setTurnManager(TurnManager turnManager) {
            this.game.turnManager = turnManager;
            return this;
        }

        public Game buildGame() {
            if(Objects.isNull(this.game.turnManager)) {
                System.out.println("Turn manager is not initialized");
            }
            if(Objects.isNull(this.game.dices)) {
                System.out.println("Dices are not initialized");
            }
            if(Objects.isNull(this.game.players)) {
                System.out.println("Players are not initialized");
            }
            if(Objects.isNull(this.game.board)) {
                System.out.println("Board is not initialized");
            }
            this.game.turnManager.setNumberOfPlayers(this.game.players.size());
            return game;
        }

    }

}
