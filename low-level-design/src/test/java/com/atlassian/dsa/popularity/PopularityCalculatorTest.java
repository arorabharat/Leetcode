package com.atlassian.dsa.popularity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopularityCalculatorTest {

    @Test
    void testBaseCase() {
        PopularityCalculator.MostPopularImplementation mostPopularImplementation = new PopularityCalculator.MostPopularImplementation();
        mostPopularImplementation.increasePopularity(1);
        mostPopularImplementation.increasePopularity(1);
        mostPopularImplementation.increasePopularity(1);
        mostPopularImplementation.increasePopularity(1);
        assertEquals(1, mostPopularImplementation.mostPopular());
    }

    @Test
    void testBaseCase1() {
        PopularityCalculator.MostPopularImplementation mostPopularImplementation = new PopularityCalculator.MostPopularImplementation();
        mostPopularImplementation.increasePopularity(1);
        mostPopularImplementation.increasePopularity(1);
        mostPopularImplementation.increasePopularity(1);
        mostPopularImplementation.increasePopularity(2);
        mostPopularImplementation.increasePopularity(2);
        mostPopularImplementation.increasePopularity(2);
        mostPopularImplementation.increasePopularity(2);
        mostPopularImplementation.increasePopularity(3);
        mostPopularImplementation.increasePopularity(3);
        assertEquals(2, mostPopularImplementation.mostPopular());
    }
}