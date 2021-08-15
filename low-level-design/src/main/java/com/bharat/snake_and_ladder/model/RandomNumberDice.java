package com.bharat.snake_and_ladder.model;

import java.util.Random;

public class RandomNumberDice implements Dice {

    private final Random random = new Random();
    private final int maxNumber;
    private final int minNumber;

    public RandomNumberDice(int minNumber, int maxNumber) {
        this.minNumber = minNumber;
        this.maxNumber = maxNumber;

    }

    @Override
    public int rollTheDice() {
        return 1;
//        return minNumber + random.nextInt(maxNumber - minNumber + 1);
    }
}
