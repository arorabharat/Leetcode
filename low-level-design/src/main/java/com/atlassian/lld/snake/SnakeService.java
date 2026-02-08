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

class SnakeGameImpl extends SnakeGameBaseImpl implements SnakeGame {

    private int currentMove = 0;

    public SnakeGameImpl(int rows, int cols) {
        super(rows, cols);
    }

    @Override
    protected boolean checkGrowthAndPostProcess(Point nextHead) {
        currentMove++;
        return currentMove % 5 == 0;
    }

    @Override
    public void print() {
        System.out.println("Move: " + currentMove);
        System.out.println("Body: " + body);
    }
}

// ... (Direction and Point classes remain the same)

abstract class SnakeGameBaseImpl implements SnakeGame {
    final LinkedList<Point> body = new LinkedList<>();
    final Set<Point> bodyLookup = new HashSet<>();
    final int rows, cols;
    boolean isGameOver = false;

    public SnakeGameBaseImpl(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        initialiseGame();
    }

    private void initialiseGame() {
        // Start head at (0,2), neck (0,1), tail (0,0)
        for (int i = 2; i >= 0; i--) {
            Point p = new Point(0, i);
            body.add(p);
            bodyLookup.add(p);
        }
    }

    Point getNextPoint(Direction dir, Point head) {
        int nextX = (head.x + dir.dx + rows) % rows;
        int nextY = (head.y + dir.dy + cols) % cols;
        return new Point(nextX, nextY);
    }

    @Override
    public void moveSnake(Direction dir) {
        if (isGameOver) return;

        Point head = body.peekFirst();
        Point nextHead = getNextPoint(dir, head);

        // Requirement: Head/Tail Switch logic
        if (body.size() > 1 && nextHead.equals(body.get(1))) {
            Collections.reverse(body);
            head = body.peekFirst();
            nextHead = getNextPoint(dir, head);
        }

        // Check self-collision
        if (bodyLookup.contains(nextHead) && !nextHead.equals(body.peekLast())) {
            isGameOver = true;
            return;
        }

        // Abstract check for growth
        boolean shouldGrow = checkGrowthAndPostProcess(nextHead);

        // Execute Move
        body.addFirst(nextHead);
        bodyLookup.add(nextHead);

        if (!shouldGrow) {
            Point tail = body.removeLast();
            bodyLookup.remove(tail);
        }
    }

    /**
     * Subclasses define when the snake grows.
     *
     * @return true if snake should grow this move.
     */
    protected abstract boolean checkGrowthAndPostProcess(Point nextHead);

    @Override
    public boolean isGameOver() {
        return isGameOver;
    }
}

/**
 * Implementation 2: Growth based on Food that expires after Y moves.
 */
class TimedFoodSnakeGameImpl extends SnakeGameBaseImpl {
    private Point food;
    private int foodExpiryMove;
    private final int lifespan;
    private int currentMove = 0;
    private final Random random = new Random();

    public TimedFoodSnakeGameImpl(int rows, int cols, int lifespan) {
        super(rows, cols);
        this.lifespan = lifespan;
        spawnFood();
    }

    private void spawnFood() {
        while (true) {
            Point p = new Point(random.nextInt(rows), random.nextInt(cols));
            if (!bodyLookup.contains(p)) {
                this.food = p;
                this.foodExpiryMove = currentMove + lifespan;
                break;
            }
        }
    }

    @Override
    protected boolean checkGrowthAndPostProcess(Point nextHead) {
        currentMove++;

        // 1. Check if existing food expired
        if (currentMove > foodExpiryMove) {
            spawnFood();
        }

        // 2. Check if head reached food
        if (nextHead.equals(food)) {
            spawnFood(); // Move food to new spot
            return true; // Snake grows
        }

        return false;
    }

    @Override
    public void print() {
        System.out.println("Move: " + currentMove + " | Food: " + food + " (Expires at " + foodExpiryMove + ")");
        System.out.println("Body: " + body);
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
