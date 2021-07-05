package tic_tac_toe;

import io.bharat.interview.mc.tictactoe.model.Cell;
import io.bharat.interview.mc.tictactoe.model.Move;
import io.bharat.interview.mc.tictactoe.model.Symbol;
import io.bharat.interview.mc.tictactoe.model.TicTacToe;

public class UntimedTicTacToe implements TicTacToe {

    private final int numOfRows;
    private final int numOfCols;

    private Cell[][] board;

    public UntimedTicTacToe(int numOfRows, int numOfCols) {
        this.numOfRows = numOfRows;
        this.numOfCols = numOfCols;
        board = new Cell[numOfRows][numOfCols];
    }

    @Override
    public boolean isTimed() {
        return false;
    }

    @Override
    public boolean isGameFinished() {
        return false;
    }

    @Override
    public void move(Move move) {
        if (!this.isValidRange(move)) {
            throw new InvalidMoveException(move + " is not a valid move.Move row/col is out of range.");
        }
        if (!isCellEmpty(move)) {
            throw new InvalidMoveException(move + " is not a valid move.Move cell is not empty");
        }
        this.board[move.getRow()][move.getCol()].setSymbol(move.getSymbol());
    }

    private boolean isValidRange(Move move) {
        boolean validRow = 0 <= move.getRow() && move.getRow() < this.numOfRows;
        boolean validCol = 0 <= move.getCol() && move.getCol() < this.numOfCols;
        return validRow && validCol;
    }

    private boolean isCellEmpty(Move move) {
        return board[move.getRow()][move.getCol()].getSymbol() == Symbol.EMPTY;
    }
}
