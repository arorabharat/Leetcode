package com.bharat.snake_and_ladder.model;

import java.util.ArrayList;
import java.util.List;

/**
 * https://workat.tech/machine-coding/editorial/how-to-design-snake-and-ladder-machine-coding-ehskk9c40x2w
 * <p>
 * <p>
 * Methods
 */
public class Game {

    private Board board;
    private final TurnManager turnManager;

    private final List<Player> players;
    private final List<Dice> dices;

    private Game() {
        this.board = Board.BoardBuilder.newInstance().setSize(100).buildBoard(); // should be part of the builder
        this.turnManager = new CyclicTurnManager(0); // should be part of the builder
        this.players = new ArrayList<>();
        this.dices = new ArrayList<>();
    }

    // game will finish if there is only one player left
    public boolean isGameFinished() {
        int leftPLayers = 0;
        for (Player player : players) {
            if (!board.isLastCell(player.getCurrentCell())) {
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
            int currentTurn = turnManager.nextTurn();
            Player currentPlayer = players.get(currentTurn);
            // skip the player if player has already won
            while (board.isLastCell(currentPlayer.getCurrentCell())) {
                currentTurn = turnManager.nextTurn();
                currentPlayer = players.get(currentTurn);
            }
            // roll all the dice and take the total count
            int totalDiceNumber = 0;
            for (Dice dice : dices) {
                totalDiceNumber = totalDiceNumber + dice.rollTheDice();
            }
            // set the final cell of the current player
            Cell finalCell = board.moveAheadNStep(currentPlayer.getCurrentCell(), totalDiceNumber);
            currentPlayer.setCurrentCell(finalCell);
            // if player has won the game print
            if (board.isLastCell(currentPlayer.getCurrentCell())) {
                System.out.println(currentPlayer.getName() + " has won the game");
            }
        }
        System.out.println("Game is finished");
    }


    public static class GameBuilder {

        Game game;

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

        public GameBuilder addPlayer(Player player) {
            this.game.players.add(player);
            this.game.turnManager.setNumberOfPlayers(this.game.players.size());
            return this;
        }

        public GameBuilder addDice(Dice dice) {
            this.game.dices.add(dice);
            return this;
        }

        public GameBuilder setTurnManager(TurnManager turnManager) {
            this.setTurnManager(turnManager);
            return this;
        }

        public GameBuilder addJumper(Jumper jumper) {
            this.game.board.addJumper(jumper);
            return this;
        }

    }

}
