package tic_tac_toe;

import io.bharat.interview.mc.tictactoe.model.Move;
import io.bharat.interview.mc.tictactoe.model.Player;
import io.bharat.interview.mc.tictactoe.model.Symbol;

import java.util.Scanner;

public class Game {

    private final GameBoard gameBoard;
    private final Player[] player;
    private final TurnManager turnManager;
    Scanner scanner = new Scanner(System.in);

    private final int NUM_OF_PLAYERS = 2;

    private void initialisePlayers() {
        this.player[turnManager.getCurrentTurn()] = new Player(Symbol.CIRCLE, turnManager.getCurrentTurn() + 1);
        this.player[turnManager.getNextTurn()] = new Player(Symbol.CROSS, turnManager.getCurrentTurn() + 1);
    }

    public Game() {
        this.gameBoard = new GameBoard();
        this.player = new Player[NUM_OF_PLAYERS];
        this.turnManager = new TurnManager(NUM_OF_PLAYERS);
        initialisePlayers();
    }

    private void makeMove(Move move) {
    }

    private boolean isFinished() {
        return false;
    }

    public void startGame() {
        System.out.println("Game starts now");
        System.out.println("Player 1 is assigned : " + this.player[0].getSymbol());
        System.out.println("Player 2 is assigned : " + this.player[1].getSymbol());
        while (!this.isFinished()) {
            int nextTurn = this.turnManager.getNextTurn();
            Player currentPlayer = this.player[nextTurn];
            gameBoard.printGameBoard();
            Move move = inputMove(currentPlayer);
            gameBoard.markMove(move);
        }
    }

    private Move inputMove(Player player) {
        Move inputMove;
        while (true) {
            System.out.println("Enter Player " + player.getId() + " move.");
            System.out.print("Row : ");
            int row = scanner.nextInt();
            System.out.print("Col : ");
            int col = scanner.nextInt();
            inputMove = new Move(row - 1, col - 1, player.getSymbol());
            if (!gameBoard.isValidRange(inputMove)) {
                System.out.println("Invalid Move !! " + inputMove + " is out of range");
                System.out.println("Input again.");
                continue;
            }
            if (!gameBoard.isCellEmpty(inputMove)) {
                System.out.println("Invalid Move !! " + inputMove + " cell is not empty");
                System.out.println("Input again.");
                continue;
            }
            return inputMove;
        }
    }
}
