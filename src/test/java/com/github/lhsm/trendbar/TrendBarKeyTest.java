package com.github.lhsm.trendbar;


import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TrendBarKeyTest {

    private TrendBarKey minute = new TrendBarKey("eurusd", 1_000, TimeUnit.MINUTES);
    private TrendBarKey hour = new TrendBarKey("eurusd", 1_000, TimeUnit.HOURS);
    private TrendBarKey day = new TrendBarKey("eurusd", 1_000, TimeUnit.DAYS);

    @Test
    public void minuteKey() {
        Assert.assertEquals(minute, new TrendBarKey(minute.getSymbol(), 58_000, minute.getPeriod()));
        Assert.assertNotEquals(minute, new TrendBarKey(minute.getSymbol(), 60_000, minute.getPeriod()));
    }

    @Test
    public void hourKey() {
        Assert.assertEquals(hour, new TrendBarKey(hour.getSymbol(), 60_000 * 60 - 1, hour.getPeriod()));
        Assert.assertNotEquals(hour, new TrendBarKey(hour.getSymbol(), 60_000 * 60, hour.getPeriod()));
    }

    @Test
    public void dayKey() {
        Assert.assertEquals(day, new TrendBarKey(day.getSymbol(), 60_000 * 60 * 24 - 1, day.getPeriod()));
        Assert.assertNotEquals(day, new TrendBarKey(day.getSymbol(), 60_000 * 60 * 24, day.getPeriod()));
    }

    @Test
    public void start() {
        Assert.assertEquals(minute.getStart(), 0);
        Assert.assertEquals(new TrendBarKey("eurusd", 60_000, TimeUnit.MINUTES).getStart(), 60_000);
        Assert.assertEquals(new TrendBarKey("eurusd", 61_000, TimeUnit.MINUTES).getStart(), 60_000);
        Assert.assertEquals(hour.getStart(), 0);
        Assert.assertEquals(new TrendBarKey("eurusd", 60_000 * 61, TimeUnit.HOURS).getStart(), 60_000 * 60);
        Assert.assertEquals(new TrendBarKey("eurusd", 60_000 * 60, TimeUnit.HOURS).getStart(), 60_000 * 60);
        Assert.assertEquals(day.getStart(), 0);
        Assert.assertEquals(new TrendBarKey("eurusd", 60_000 * 60 * 25, TimeUnit.DAYS).getStart(), 60_000 * 60 * 24);
        Assert.assertEquals(new TrendBarKey("eurusd", 60_000 * 60 * 24, TimeUnit.DAYS).getStart(), 60_000 * 60 * 24);
    }

    @Test
    public void finish() {
        Assert.assertEquals(minute.getFinish(), 60_000);
        Assert.assertEquals(hour.getFinish(), 60_000 * 60);
        Assert.assertEquals(day.getFinish(), 60_000 * 60 * 24);
    }

    @Test
    public void isCompleted() {
        Assert.assertFalse(minute.isCompleted(1_000));
        Assert.assertTrue(minute.isCompleted(60_000));
        Assert.assertTrue(minute.isCompleted(100_000));
    }

}