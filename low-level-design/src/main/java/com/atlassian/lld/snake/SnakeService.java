package com.atlassian.lld.snake;

import java.util.*;


interface SnakeGame {
    void moveSnake(Direction direction);

    boolean isGameOver();

    void print();
}

enum Direction {

    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    final int dx, dy;

    Direction(int dx, int dy) {
        this.dx = dx;
        this.dy = dy;
    }
}

class Point {
    int x, y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point point)) return false;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

class SnakeGameImpl implements SnakeGame {
    private final int rows, cols;
    private final LinkedList<Point> body = new LinkedList<>();
    private final Set<Point> bodyLookup = new HashSet<>();
    private int moveCount = 0;
    private boolean isGameOver = false;

    public SnakeGameImpl(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        for (int i = 0; i < 3; i++) {
            Point p = new Point(0, i); // Head at (0,2), Tail at (0,0)
            body.add(p);
            bodyLookup.add(p);
        }
    }

    @Override
    public void moveSnake(Direction dir) {

        if (isGameOver) return;

        Point head = body.peekFirst();
        assert head != null;

        Point nextHead = nextPoint(dir, head);

        // Requirement 4: Head/Tail Switch logic
        // If there's at least 2 segments, check if moving into the 'neck'
        if (body.size() > 1) {
            Point neck = body.get(1);
            if (nextHead.equals(neck)) {
                Collections.reverse(body);
                head = body.peekFirst();
                assert head != null;
                nextHead = nextPoint(dir, head);
            }
        }

        // Check self-collision (Game Over)
        // Note: nextHead could be the current tail, which will move, so that's safe.
        if (bodyLookup.contains(nextHead) && !nextHead.equals(body.peekLast())) {
            this.isGameOver = true;
            return;
        }

        // Requirement 2: Growth every 5 moves
        moveCount++;
        boolean shouldGrow = (moveCount % 5 == 0);

        // Execute Move
        body.addFirst(nextHead);
        bodyLookup.add(nextHead);

        if (!shouldGrow) {
            Point tail = body.removeLast();
            bodyLookup.remove(tail);
        }
    }

    private Point nextPoint(Direction dir, Point head) {
        int nextX = (head.x + dir.dx + rows) % rows;
        int nextY = (head.y + dir.dy + cols) % cols;
        return new Point(nextX, nextY);
    }

    @Override
    public boolean isGameOver() {
        return isGameOver;
    }

    @Override
    public void print() {
        System.out.println("Move: " + moveCount + " | Body: " + body);
    }
}

public class SnakeService {

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGameImpl(100, 100);
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
