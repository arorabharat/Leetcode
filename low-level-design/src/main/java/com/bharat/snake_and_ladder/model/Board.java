package com.bharat.snake_and_ladder.model;

import java.util.HashMap;
import java.util.Map;

public class Board {

    private final Cell[] cells;
    private final int totalNumberOfCells;

    private final int START_CELL_NUMBER = 1;
    private final int END_CELL_NUMBER;

    Map<Cell, CrossCellElement> map;

    public Board(int totalNumberOfCells) {
        this.totalNumberOfCells = totalNumberOfCells;
        this.map = new HashMap<>();
        this.cells = new Cell[totalNumberOfCells];
        for (int i = 0; i < totalNumberOfCells; i++) {
            cells[i] = new Cell(i);
        }
        END_CELL_NUMBER = totalNumberOfCells;
    }

    private boolean isInvalid(int cellNumber) {
        return 0 > cellNumber || cellNumber >= totalNumberOfCells;
    }

    public Cell getCell(int cellNumber) {
        if (isInvalid(cellNumber)) {
            throw new IllegalArgumentException("Invalid cell number");
        }
        return cells[cellNumber];
    }

    public void addCrossCellElement(CrossCellElement crossCellElement) {
        int startingCellNumber = crossCellElement.startCell().getNumber();
        int endingCellNumber = crossCellElement.endCell().getNumber();
        if (isInvalid(startingCellNumber) || isInvalid(endingCellNumber)) {
            throw new IllegalArgumentException("Invalid cell number");
        }
        this.map.put(crossCellElement.startCell(), crossCellElement);
    }

    public Cell moveAheadNStep(Cell fromCell, int numberOfCells) {
        int endCellNumber = fromCell.getNumber() + numberOfCells;
        if (isInvalid(endCellNumber)) {
            return fromCell;
        }
        Cell endCell = getCell(endCellNumber);
        while (this.map.containsKey(endCell)) {
            endCell = this.map.get(endCell).endCell();
        }
        return endCell;
    }
}
