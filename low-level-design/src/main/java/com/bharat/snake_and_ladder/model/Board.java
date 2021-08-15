package com.bharat.snake_and_ladder.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Assumption
 * 1. Start cell would be always 1
 * 2. End cell would always be the last cell
 * <p>
 * Methods
 * moveNSteps(int numberOfSteps) <- to play the game (run phase)
 */
public class Board {

    private final int START_CELL_NUMBER;
    private Map<Integer, Jumper> startCellToJumperMap;
    private int boardSize;

    private Board() {
        this.boardSize = 100; // default size
        START_CELL_NUMBER = 0;
    }

    private void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    private boolean isInvalid(int cellNumber) {
        return 0 > cellNumber || cellNumber >= boardSize;
    }

    public int getStartCell() {
        return START_CELL_NUMBER;
    }

    public int getLastCell() {
        return boardSize - 1;
    }

    public int moveAheadNStep(int fromCell, int numberOfCells) {
        int endCellNumber = fromCell + numberOfCells;
        if (isInvalid(endCellNumber)) {
            return fromCell;
        }
        while (this.startCellToJumperMap.containsKey(endCellNumber)) {
            System.out.println(this.startCellToJumperMap.get(endCellNumber));
            endCellNumber = this.startCellToJumperMap.get(endCellNumber).getEnd();
        }
        return endCellNumber;
    }

    public static class BoardBuilder {

        private final Board board;

        public BoardBuilder() {
            this.board = new Board();
        }

        public static BoardBuilder newInstance() {
            return new BoardBuilder();
        }

        public BoardBuilder setSize(int boardSize) {
            board.boardSize = boardSize;
            return this;
        }

        private boolean isInvalid(int cellNumber) {
            return 0 > cellNumber || cellNumber >= board.boardSize;
        }

        public BoardBuilder setJumpers(List<Jumper> jumpers) {
            this.board.startCellToJumperMap = new HashMap<>();
            for (Jumper jumper : jumpers) {
                int start = jumper.getStart();
                int end = jumper.getEnd();
                if (isInvalid(start) || isInvalid(end)) {
                    throw new IllegalArgumentException("Invalid jumper : " + jumper);
                }
                this.board.startCellToJumperMap.put(start, jumper);
            }
            return this;
        }

        public Board buildBoard() {
            return board;
        }
    }
}
