package tic_tac_toe;

import io.bharat.interview.mc.tictactoe.model.Cell;
import io.bharat.interview.mc.tictactoe.model.Move;
import io.bharat.interview.mc.tictactoe.model.Symbol;

public class GameBoard {

    private final int size;
    private final Cell[][] board;

    public GameBoard(int size) {
        if (size < 2) throw new IllegalArgumentException("Invalid size !! Size should be more than 1");
        this.size = size;
        board = new Cell[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                board[i][j] = new Cell();
            }
        }
    }

    public void printGameBoard() {
        for (int i = 0; i < size; i++) {
            System.out.print("| ");
            for (int j = 0; j < size; j++) {
                System.out.print(board[i][j].getSymbol().toString() + " | ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public boolean isValidRange(Move move) {
        boolean validRow = 0 <= move.getRow() && move.getRow() < size;
        boolean validCol = 0 <= move.getCol() && move.getCol() < size;
        return validRow && validCol;
    }

    public boolean isCellEmpty(Move move) {
        return board[move.getRow()][move.getCol()].getSymbol() == Symbol.EMPTY;
    }

    private boolean isCellEmpty(int i, int j) {
        return board[i][j].getSymbol() == Symbol.EMPTY;
    }

    public boolean hasEmptyCell() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j].getSymbol() == Symbol.EMPTY) return true;
            }
        }
        return false;
    }

    public boolean hasDiagonalLine() {
        if (isCellEmpty(0, 0)) return false;
        boolean res = true;
        for (int i = 1; i < size; i++) {
            if (board[i][i].getSymbol() != board[0][0].getSymbol()) {
                res = false;
                break;
            }
        }
        if (res) {
            System.out.println("Made Diagonal line");
            return true;
        }
        if (isCellEmpty(0, size - 1)) return false;
        for (int i = 1; i < size; i++) {
            if (board[i][size - 1 - i].getSymbol() != board[0][0].getSymbol()) {
                return false;
            }
        }
        System.out.println("Made Diagonal line");
        return true;
    }

    public boolean hasHorizontalLine() {
        for (int i = 0; i < size; i++) {
            if (isCellEmpty(i, 0)) return false;
            boolean res = true;
            for (int j = 1; j < size; j++) {
                if (board[i][j].getSymbol() != board[0][0].getSymbol()) {
                    res = false;
                    break;
                }
            }
            if (res) {
                System.out.println("Made horizontal line");
                return true;
            }
        }
        return false;
    }

    public boolean hasVerticalLine() {
        for (int j = 0; j < size; j++) {
            if (isCellEmpty(0, j)) {
                return false;
            }
            boolean res = true;
            for (int i = 1; i < size; i++) {
                if (board[j][i].getSymbol() != board[0][0].getSymbol()) {
                    res = false;
                    break;
                }
            }
            if (res) {
                System.out.println("Made Vertical line");
                return true;
            }
        }
        return false;
    }

    public void markMove(Move move) {
        if (!isValidRange(move)) {
            throw new InvalidMoveException(move + " is not a valid move.Move row/col is out of range.");
        }
        if (!isCellEmpty(move)) {
            throw new InvalidMoveException(move + " is not a valid move.Move cell is not empty");
        }
        this.board[move.getRow()][move.getCol()].setSymbol(move.getSymbol());
    }
}