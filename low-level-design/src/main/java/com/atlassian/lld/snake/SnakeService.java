package com.atlassian.lld.snake;

import java.util.*;

enum Direction {
    UP(-1, 0),
    DOWN(1, 0),
    LEFT(0, -1),
    RIGHT(0, 1);

    final int dx, dy;
    Direction(int dx, int dy) { this.dx = dx; this.dy = dy; }
}


interface SnakeGame {
    void moveSnake(Direction direction);

    boolean isGameOver();

    void print();
}

class Point {

    int x;
    int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
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
    private final int rows, cols;
    private final Deque<Point> body = new LinkedList<>();
    private final Set<Point> bodyLookup = new HashSet<>();
    private Point food;
    private boolean isGameOver = false;

    public SnakeGameImpl(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        // Start snake at (0,0)
        Point start = new Point(0, 0);
        body.add(start);
        bodyLookup.add(start);
        spawnFood();
    }

    private void spawnFood() {
        // Simple random spawn (could be optimized)
        Random rand = new Random();
        while (true) {
            Point p = new Point(rand.nextInt(rows), rand.nextInt(cols));
            if (!bodyLookup.contains(p)) {
                this.food = p;
                break;
            }
        }
    }

    @Override
    public void moveSnake(Direction dir) {
        if (isGameOver) return;

        Point head = body.peekFirst();
        Point nextHead = new Point(head.x + dir.dx, head.y + dir.dy);

        // 1. Check Wall Collision
        if (nextHead.x < 0 || nextHead.x >= rows || nextHead.y < 0 || nextHead.y >= cols) {
            isGameOver = true;
            return;
        }

        // 2. Check Self-Collision (excluding tail if it's about to move)
        // If nextHead is current tail, it won't be a collision because tail moves up.
        if (bodyLookup.contains(nextHead) && !nextHead.equals(body.peekLast())) {
            isGameOver = true;
            return;
        }

        // 3. Move Logic
        body.addFirst(nextHead);
        bodyLookup.add(nextHead);

        if (nextHead.equals(food)) {
            spawnFood(); // Grow: don't remove tail
        } else {
            Point tail = body.removeLast();
            bodyLookup.remove(tail);
        }
    }

    @Override
    public boolean isGameOver() { return isGameOver; }

    @Override
    public void print() {
        System.out.println("Snake Head at: (" + body.peekFirst().x + "," + body.peekFirst().y + ")");
    }
}


public class SnakeService {

    public static void main(String[] args) {
        SnakeGame snakeGame = new SnakeGameImpl(100,100);
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
