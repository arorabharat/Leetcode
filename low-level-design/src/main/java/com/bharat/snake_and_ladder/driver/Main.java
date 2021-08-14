package com.bharat.snake_and_ladder.driver;

import com.bharat.snake_and_ladder.model.Board;
import com.bharat.snake_and_ladder.model.Cell;
import com.bharat.snake_and_ladder.model.CrossCellElement;
import com.bharat.snake_and_ladder.model.Ladder;

public class Main {

    public static void main(String[] args) {
        Board board = new Board(100);
        Cell c2 = board.getCell(2);
        Cell c98 = board.getCell(98);
        Cell c30 = board.getCell(30);
        Cell c10 = board.getCell(10);
        CrossCellElement ladder1 = new Ladder(c2, c98);
        CrossCellElement ladder2 = new Ladder(c2, c30);
        CrossCellElement snake1 = new Ladder(c30, c10);
        board.addCrossCellElement(ladder1);
        board.addCrossCellElement(ladder2);
        board.addCrossCellElement(snake1);
    }
}
