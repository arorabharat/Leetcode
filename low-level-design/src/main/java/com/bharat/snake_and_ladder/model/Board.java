package com.bharat.snake_and_ladder.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Assumption
 * 1. Start cell would be always 1
 * 2. End cell would always be the last cell
 * <p>
 * Methods
 * addJumper(Jumper jumper); <- to build the game (build phase)
 * getCell(int cellNumber); <- to build the game (build phase)
 * moveNSteps(int numberOfSteps) <- to play the game (run phase)
 */
public class Board {

    private int[] cells;

    private int boardSize;
    private final int START_CELL_NUMBER;
    private final int END_CELL_NUMBER;
    private final int DEFAULT_BOARD_SIZE = 100;

    Map<Cell, Jumper> startCellToJumper;

    private Board() {
        this.boardSize = DEFAULT_BOARD_SIZE;
        this.startCellToJumper = new HashMap<>();
        setBoardSize(boardSize);
        START_CELL_NUMBER = 0;
        END_CELL_NUMBER = boardSize - 1;
    }

    private void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
        this.cells = new Cell[this.boardSize];
        for (int i = 0; i < this.boardSize; i++) {
            cells[i] = new Cell(i);
        }
    }

    private boolean isInvalid(int cellNumber) {
        return 0 > cellNumber || cellNumber >= boardSize;
    }

    public Cell getCell(int cellNumber) {
        if (isInvalid(cellNumber)) {
            throw new IllegalArgumentException("Invalid cell number");
        }
        return cells[cellNumber];
    }

    public Cell getStartCell() {
        return cells[START_CELL_NUMBER];
    }

    public boolean isLastCell(Cell cell) {
        return cell.getNumber() == END_CELL_NUMBER;
    }

    public Cell moveAheadNStep(Cell fromCell, int numberOfCells) {
        int endCellNumber = fromCell.getNumber() + numberOfCells;
        if (isInvalid(endCellNumber)) {
            return fromCell;
        }
        Cell endCell = getCell(endCellNumber);
        while (this.startCellToJumper.containsKey(endCell)) {
            endCell = this.startCellToJumper.get(endCell).end();
        }
        return endCell;
    }

    public static class BoardBuilder {

        private Board board;

        public BoardBuilder() {
            this.board = new Board();
        }

        public static BoardBuilder newInstance() {
            return new BoardBuilder();
        }

        public BoardBuilder setSize(int boardSize) {
            board.setBoardSize(boardSize);
            return this;
        }

        private boolean isInvalid(int cellNumber) {
            return 0 > cellNumber || cellNumber >= board.boardSize;
        }

        public BoardBuilder addJumper(Jumper jumper) {
            int startingCellNumber = jumper.start().getNumber();
            int endingCellNumber = jumper.end().getNumber();
            if (isInvalid(startingCellNumber) || isInvalid(endingCellNumber)) {
                throw new IllegalArgumentException("Invalid cell number");
            }
            this.board.startCellToJumper.put(jumper.start(), jumper);
            return this;
        }

        public Board buildBoard() {
            return board;
        }
    }
}
