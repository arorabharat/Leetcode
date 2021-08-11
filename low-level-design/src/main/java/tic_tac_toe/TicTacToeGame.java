package tic_tac_toe;

import io.bharat.interview.mc.tictactoe.model.Move;
import io.bharat.interview.mc.tictactoe.model.Player;
import io.bharat.interview.mc.tictactoe.model.Symbol;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TicTacToeGame implements Game {

    private static final int BOARD_SIZE = 3;
    private static final int NUM_OF_COLS = 3;
    private static int NUM_OF_PLAYERS = 2;
    private final GameBoard gameBoard;
    private final List<Player> player;
    private final TurnManager turnManager;
    Scanner scanner = new Scanner(System.in);

    public TicTacToeGame() {
        this.gameBoard = new GameBoard(BOARD_SIZE);
        this.player = new ArrayList<>();
        this.turnManager = new CyclicTurnManager(NUM_OF_PLAYERS);
        this.player.add(new Player(Symbol.CIRCLE, 0));
        this.player.add(new Player(Symbol.CROSS, 1));
    }

    @Override
    public boolean isFinished() {
        if (gameBoard.hasHorizontalLine() || gameBoard.hasVerticalLine() || gameBoard.hasDiagonalLine()) {
            System.out.println("I am true");
            return true;
        }
        return !gameBoard.hasEmptyCell();
    }

    private Move inputMove(Player player) {
        Move inputMove;
        while (true) {
            System.out.println("Enter Player " + player.getName() + " move.");
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

    @Override
    public void start() {
        System.out.println("Game starts now");
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            System.out.println("Input player " + (i + 1) + " name : ");
            String name = scanner.next();
            this.player.get(i).setName(name);
            System.out.println(this.player.get(i));
        }
        while (!isFinished()) {
            int nextTurn = this.turnManager.getNextTurn();
            Player currentPlayer = this.player.get(nextTurn);
            gameBoard.printGameBoard();
            Move move = inputMove(currentPlayer);
            gameBoard.markMove(move);
        }
        gameBoard.printGameBoard();
    }
}
