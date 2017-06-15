package com.github.lhsm.trendbar;

import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TrendBarMemoryStorageTest {

    private TrendBarStorage storage = new TrendBarMemoryStorage(100);

    private TrendBar minute = TrendBar.minuteOf("EURUSD", 1000);

    @Test
    public void find() throws Exception {
        storage.save(minute);

        assertSize(storage.find(new TrendBarFilter(minute.getSymbol(), minute.getPeriod(), 0L, 2_000)), 1);
    }

    @Test
    public void absentSymbol() throws Exception {
        storage.save(minute);

        assertSize(storage.find(new TrendBarFilter(minute + "any", minute.getPeriod(), 0, 2_000)), 0);
    }

    @Test
    public void absentPreiod() throws Exception {
        storage.save(minute);

        assertSize(storage.find(new TrendBarFilter(minute.getSymbol(), TimeUnit.HOURS, 0, 2_000)), 0);
    }

    @Test
    public void absentTimestamp() throws Exception {
        storage.save(minute);

        assertSize(storage.find(new TrendBarFilter(minute.getSymbol(), minute.getPeriod(), 60_000, 100_000)), 0);
    }

    @Test
    public void differentTimestamp() throws Exception {
        storage.save(minute);
        storage.save(TrendBar.minuteOf(minute.getSymbol(), minute.getTimestamp() + 60_000));

        assertSize(storage.find(new TrendBarFilter(minute.getSymbol(), minute.getPeriod(), 0, 100_000)), 2);
    }

    @Test
    public void differentTimestampMissedFilter() throws Exception {
        storage.save(minute);
        storage.save(TrendBar.minuteOf(minute.getSymbol(), minute.getTimestamp() + 60_000));

        assertSize(storage.find(new TrendBarFilter(minute.getSymbol(), minute.getPeriod(), 0, 50_000)), 1);
    }

    @Test
    public void notCompleted() throws Exception {
        storage.save(TrendBar.minuteOf(minute.getSymbol(), System.currentTimeMillis() + 100_000));

        assertSize(storage.find(new TrendBarFilter(minute.getSymbol(), minute.getPeriod(), 0, System.currentTimeMillis())), 0);
    }

    @Test
    public void nullFilterShouldMatchAny() throws Exception {
        storage.save(minute);

        assertSize(storage.find(null), 1);
    }

    private void assertSize(Stream<TrendBar> stream, int size) {
        Assert.assertThat(stream.collect(Collectors.toList()).size(), CoreMatchers.is(size));
    }

}