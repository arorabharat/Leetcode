package com.atlassian.lld.snake;

import java.util.*;

enum Direction {
    UP,
    DOWN,
    LEFT,
    RIGHT
}


interface SnakeGame {
    void moveSnake(Direction direction);

    boolean isGameOver();

    void print();
}

class Pair {

    int x;
    int y;

    public Pair(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return x == pair.x && y == pair.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "{" + x +
                "," + y +
                '}';
    }
}

class SnakeGameImpl implements SnakeGame {

    private final Set<Pair> loc = new HashSet<>();
    private final Deque<Pair> snake = new LinkedList<>();
    private int totalMoves = 0;
    private boolean gameOver = false;

    // (0,1) -> (0,2) -> (0,3)
    // |
    // |
    // |
    // ---------------
    public SnakeGameImpl() {

        Pair h = new Pair(1, 0);
        Pair m = new Pair(2, 0);
        Pair t = new Pair(3, 0);

        snake.add(h);
        snake.add(m);
        snake.add(t);

        loc.add(h);
        loc.add(m);
        loc.add(t);
    }

    private void move(int dx, int dy) {
        if (!isGameOver()) {
            this.totalMoves++;
            if (this.totalMoves % 5 != 0) {
                removeTail();
            }
            addHead(dx, dy);
        }
    }

    private void addHead(int dx, int dy) {
        Pair oldHead = snake.peekFirst();
        assert oldHead != null;
        Pair newHead = new Pair(oldHead.x + dx, oldHead.y + dy);
        if (loc.contains(newHead)) {
            this.gameOver = true;
        } else {
            snake.addFirst(newHead);
            loc.add(newHead);
        }
    }

    public void print() {
        System.out.println(snake);
        System.out.println(loc);
        System.out.println(totalMoves);
    }

    private void removeTail() {
        Pair tail = snake.removeLast();
        loc.remove(tail);
    }

    @Override
    public void moveSnake(Direction direction) {
        switch (direction) {
            case UP -> move(1, 0);
            case DOWN -> move(-1, 0);
            case LEFT -> move(0, -1);
            case RIGHT -> move(0, 1);
            default -> throw new RuntimeException("Invalid direction");
        }
    }

    @Override
    public boolean isGameOver() {
        return this.gameOver;
    }
}


public class SnakeService {

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGameImpl();
        snakeGame.print();
        snakeGame.moveSnake(Direction.RIGHT);
        System.out.println(snakeGame.isGameOver());
        snakeGame.print();
        snakeGame.moveSnake(Direction.RIGHT);
        System.out.println(snakeGame.isGameOver());
        snakeGame.print();
        snakeGame.moveSnake(Direction.RIGHT);
        System.out.println(snakeGame.isGameOver());
        snakeGame.print();
        snakeGame.moveSnake(Direction.UP);
        System.out.println(snakeGame.isGameOver());
        snakeGame.print();
        snakeGame.moveSnake(Direction.LEFT);
        System.out.println(snakeGame.isGameOver());
        snakeGame.print();
        snakeGame.moveSnake(Direction.LEFT);
        System.out.println(snakeGame.isGameOver());
        snakeGame.print();
        snakeGame.moveSnake(Direction.LEFT);
        System.out.println(snakeGame.isGameOver());
        snakeGame.print();
        snakeGame.moveSnake(Direction.LEFT);
        System.out.println(snakeGame.isGameOver());
        snakeGame.print();
        snakeGame.moveSnake(Direction.DOWN);
        System.out.println(snakeGame.isGameOver());
        snakeGame.print();
        snakeGame.moveSnake(Direction.RIGHT);
        System.out.println(snakeGame.isGameOver());
        snakeGame.print();
        snakeGame.moveSnake(Direction.UP);
        System.out.println(snakeGame.isGameOver());
        snakeGame.print();
    }
}
