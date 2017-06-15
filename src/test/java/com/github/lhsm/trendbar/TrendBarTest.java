package com.github.lhsm.trendbar;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TrendBarTest {

    private TrendBar defaultMinuteBar = TrendBar.minuteOf("eurusd", 1_000);
    private TrendBar defaultHourBar = TrendBar.hourOf("eurusd", 1_000);
    private TrendBar defaultDayBar = TrendBar.dayOf("eurusd", 1_000);

    @Test
    public void shouldUpdate() {
        TrendBar trendBar = new TrendBar(
                defaultMinuteBar.getSymbol(),
                defaultMinuteBar.getTimestamp(),
                defaultMinuteBar.getPeriod(),
                BigDecimal.valueOf(1.0)
        );

        TrendBar updatedPrice = defaultMinuteBar.add(trendBar);

        Assert.assertEquals(defaultMinuteBar.getPrice().getLow(), updatedPrice.getPrice().getLow());
        Assert.assertEquals(trendBar.getPrice().getHigh(), updatedPrice.getPrice().getHigh());
        Assert.assertEquals(defaultMinuteBar.getPrice().getOpen(), updatedPrice.getPrice().getOpen());
        Assert.assertEquals(defaultMinuteBar.getPrice().getClose(), updatedPrice.getPrice().getClose());
    }

    @Test
    public void shouldNotUpdateWithOtherSymbol() {
        TrendBar updatedPrice = defaultMinuteBar.add(TrendBar.minuteOf(defaultMinuteBar.getSymbol() + "any", defaultMinuteBar.getTimestamp()));

        Assert.assertEquals(defaultMinuteBar, updatedPrice);
    }

    @Test
    public void shouldNotUpdateWithOtherPeriod() {
        TrendBar updatedPrice = defaultMinuteBar.add(TrendBar.hourOf(defaultMinuteBar.getSymbol(), defaultMinuteBar.getTimestamp()));

        Assert.assertEquals(defaultMinuteBar, updatedPrice);
    }

    @Test
    public void shouldUpdateWithSameMinute() {
        TrendBar trendBar = new TrendBar(defaultMinuteBar.getSymbol(), defaultMinuteBar.getTimestamp() + 58_000, defaultMinuteBar.getPeriod(), BigDecimal.valueOf(1.0));
        TrendBar updatedPrice = defaultMinuteBar.add(trendBar);

        Assert.assertEquals(defaultMinuteBar.getPrice().getLow(), updatedPrice.getPrice().getLow());
        Assert.assertEquals(trendBar.getPrice().getHigh(), updatedPrice.getPrice().getHigh());
        Assert.assertEquals(defaultMinuteBar.getPrice().getOpen(), updatedPrice.getPrice().getOpen());
        Assert.assertEquals(trendBar.getPrice().getClose(), updatedPrice.getPrice().getClose());
    }

    @Test
    public void shouldNotUpdateWithOtherMinute() {
        TrendBar updatedPrice = defaultMinuteBar.add(TrendBar.minuteOf(defaultMinuteBar.getSymbol(), defaultMinuteBar.getTimestamp() + 59_000));

        Assert.assertEquals(defaultMinuteBar, updatedPrice);
    }

    @Test
    public void shouldUpdateWithSameHour() {
        TrendBar trendBar = new TrendBar(defaultHourBar.getSymbol(), defaultHourBar.getTimestamp() + 60_000 * 59, defaultHourBar.getPeriod(), BigDecimal.valueOf(1.0));
        TrendBar updatedPrice = defaultHourBar.add(trendBar);

        Assert.assertEquals(defaultHourBar.getPrice().getLow(), updatedPrice.getPrice().getLow());
        Assert.assertEquals(trendBar.getPrice().getHigh(), updatedPrice.getPrice().getHigh());
        Assert.assertEquals(defaultHourBar.getPrice().getOpen(), updatedPrice.getPrice().getOpen());
        Assert.assertEquals(trendBar.getPrice().getClose(), updatedPrice.getPrice().getClose());
    }

    @Test
    public void shouldNotUpdateWithOtherHour() {
        TrendBar updatedPrice = defaultHourBar.add(TrendBar.hourOf(defaultHourBar.getSymbol(), defaultHourBar.getTimestamp() + 60_000 * 60));

        Assert.assertEquals(defaultHourBar, updatedPrice);
    }

    @Test
    public void shouldUpdateWithSameDay() {
        TrendBar trendBar = new TrendBar(defaultDayBar.getSymbol(), defaultDayBar.getTimestamp() + 60_000 * 60 * 23, defaultDayBar.getPeriod(), BigDecimal.valueOf(1.0));
        TrendBar updatedPrice = defaultDayBar.add(trendBar);

        Assert.assertEquals(defaultDayBar.getPrice().getLow(), updatedPrice.getPrice().getLow());
        Assert.assertEquals(trendBar.getPrice().getHigh(), updatedPrice.getPrice().getHigh());
        Assert.assertEquals(defaultDayBar.getPrice().getOpen(), updatedPrice.getPrice().getOpen());
        Assert.assertEquals(trendBar.getPrice().getClose(), updatedPrice.getPrice().getClose());
    }

    @Test
    public void shouldNotUpdateWithOtherDay() {
        TrendBar updatedPrice = defaultDayBar.add(TrendBar.hourOf(defaultDayBar.getSymbol(), defaultDayBar.getTimestamp() + 60_000 * 60 * 24));

        Assert.assertEquals(defaultDayBar, updatedPrice);
    }

}