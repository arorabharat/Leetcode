package com.atlassian.dsa.streamingprices;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StreamingTicker2Test {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testBaseCase() {
        StreamingTicker2.StreamingTickerService streamingTickerService = new StreamingTicker2.StreamingTickerService();
        assertEquals(-1, streamingTickerService.getMinPrice());
        assertEquals(-1, streamingTickerService.getMaxPrice());
        assertEquals(-1, streamingTickerService.getCurrentPrice());
    }

    @Test
    void testSingleValue() {
        StreamingTicker2.StreamingTickerService streamingTickerService = new StreamingTicker2.StreamingTickerService();
        streamingTickerService.updatePrice(1, 10);
        assertEquals(10, streamingTickerService.getMinPrice());
        assertEquals(10, streamingTickerService.getMaxPrice());
        assertEquals(10, streamingTickerService.getCurrentPrice());
    }

    @Test
    void testMultiValue() {
        StreamingTicker2.StreamingTickerService streamingTickerService = new StreamingTicker2.StreamingTickerService();
        streamingTickerService.updatePrice(1, 10);
        streamingTickerService.updatePrice(2, 5);
        streamingTickerService.updatePrice(3, 7);
        assertEquals(5, streamingTickerService.getMinPrice());
        assertEquals(10, streamingTickerService.getMaxPrice());
        assertEquals(7, streamingTickerService.getCurrentPrice());
    }

    @Test
    void testOverrideValue() {
        StreamingTicker2.StreamingTickerService streamingTickerService = new StreamingTicker2.StreamingTickerService();
        streamingTickerService.updatePrice(1, 10);
        streamingTickerService.updatePrice(2, 5);
        streamingTickerService.updatePrice(3, 7);
        streamingTickerService.updatePrice(1, 2);
        streamingTickerService.updatePrice(4, 1);
        assertEquals(1, streamingTickerService.getMinPrice());
        assertEquals(7, streamingTickerService.getMaxPrice());
        assertEquals(1, streamingTickerService.getCurrentPrice());
    }
}