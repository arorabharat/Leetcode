package com.atlassian.dsa.streamingprices;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreamingTicker1Test {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testBaseCase() {
        StreamingTicker1.StreamingPrice streamingPrice = new StreamingTicker1.StreamingPrice();
        assertEquals(Integer.MAX_VALUE, streamingPrice.getMinPrice());
        assertEquals(Integer.MIN_VALUE, streamingPrice.getMaxPrice());
        assertEquals(-1, streamingPrice.getCurrentPrice());
    }

    @Test
    void testWithOnePrice() {
        StreamingTicker1.StreamingPrice streamingPrice = new StreamingTicker1.StreamingPrice();
        streamingPrice.updatePrice(1, 10);
        assertEquals(10, streamingPrice.getMinPrice());
        assertEquals(10, streamingPrice.getMaxPrice());
        assertEquals(10, streamingPrice.getCurrentPrice());
    }

    @Test
    void testWithPriceCorrection() {
        StreamingTicker1.StreamingPrice streamingPrice = new StreamingTicker1.StreamingPrice();
        streamingPrice.updatePrice(1, 10);
        streamingPrice.updatePrice(1, 5);
        assertEquals(5, streamingPrice.getMinPrice());
        assertEquals(5, streamingPrice.getMaxPrice());
        assertEquals(5, streamingPrice.getCurrentPrice());
    }

    @Test
    void testWithMultiplePrice() {
        StreamingTicker1.StreamingPrice streamingPrice = new StreamingTicker1.StreamingPrice();
        streamingPrice.updatePrice(1, 10);
        streamingPrice.updatePrice(1, 5);
        streamingPrice.updatePrice(2, 15);
        streamingPrice.updatePrice(3, 7);
        streamingPrice.updatePrice(1, 1);
        assertEquals(1, streamingPrice.getMinPrice());
        assertEquals(15, streamingPrice.getMaxPrice());
        assertEquals(7, streamingPrice.getCurrentPrice());
    }

}