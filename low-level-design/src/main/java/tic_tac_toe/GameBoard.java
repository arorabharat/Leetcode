package tic_tac_toe;

import io.bharat.interview.mc.tictactoe.model.Cell;
import io.bharat.interview.mc.tictactoe.model.Move;
import io.bharat.interview.mc.tictactoe.model.Symbol;

public class GameBoard {

    private static final int NUM_OF_ROWS = 3;
    private static final int NUM_OF_COLS = 3;
    private final Cell[][] board;

    public GameBoard() {
        board = new Cell[NUM_OF_ROWS][NUM_OF_COLS];
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            for (int j = 0; j < NUM_OF_COLS; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public void printGameBoard() {
        for (int i = 0; i < NUM_OF_ROWS; i++) {
            System.out.print("| ");
            for (int j = 0; j < NUM_OF_COLS; j++) {
                System.out.print(board[i][j].getSymbol().toString()+" | ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isValidRange(Move move) {
        boolean validRow = 0 <= move.getRow() && move.getRow() < NUM_OF_ROWS;
        boolean validCol = 0 <= move.getCol() && move.getCol() < NUM_OF_COLS;
        return validRow && validCol;
    }

    public boolean isCellEmpty(Move move) {
        return board[move.getRow()][move.getCol()].getSymbol() == Symbol.EMPTY;
    }

    public void markMove(Move move) {
        if (!this.isValidRange(move)) {
            throw new InvalidMoveException(move + " is not a valid move.Move row/col is out of range.");
        }
        if (!isCellEmpty(move)) {
            throw new InvalidMoveException(move + " is not a valid move.Move cell is not empty");
        }
        this.board[move.getRow()][move.getCol()].setSymbol(move.getSymbol());
    }
}