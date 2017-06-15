package com.github.lhsm.trendbar;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableMap;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TrendBarKey {

    private final String symbol;

    private final long start;

    private final long finish;

    private final TimeUnit period;

    public TrendBarKey(String symbol, long timestamp, TimeUnit period) {
        this.symbol = Preconditions.checkNotNull(symbol);
        this.period = Preconditions.checkNotNull(period);
        this.start = computeStart(timestamp, period);
        finish = start + period.toMillis(1);
    }

    public String getSymbol() {
        return symbol;
    }

    public long getStart() {
        return start;
    }

    public TimeUnit getPeriod() {
        return period;
    }

    public long getFinish() {
        return finish;
    }

    public boolean isCompleted(long timestamp) {
        return timestamp >= finish;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("symbol", symbol)
                .add("start", start)
                .add("finish", finish)
                .add("period", period)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrendBarKey key = (TrendBarKey) o;
        return start == key.start &&
                Objects.equals(symbol, key.symbol) &&
                period == key.period;
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, start, period);
    }

    private long computeStart(long timestamp, TimeUnit period) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(timestamp), ZoneOffset.UTC.normalized())
                .truncatedTo(TRUNCATE_MAP.get(period));

        return Timestamp.from(dateTime.toInstant(ZoneOffset.UTC)).getTime();
    }

    private static final Map<TimeUnit, TemporalUnit> TRUNCATE_MAP = ImmutableMap.of(
            TimeUnit.DAYS, ChronoUnit.DAYS,
            TimeUnit.HOURS, ChronoUnit.HOURS,
            TimeUnit.MINUTES, ChronoUnit.MINUTES
    );
}
