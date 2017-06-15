package com.github.lhsm.trendbar;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class TrendBar {

    private final TrendBarKey key;

    private final long timestamp;

    private final TrendBarPrice price;

    public TrendBar(String symbol, long timestamp, TimeUnit period, BigDecimal price) {
        this(
                new TrendBarKey(symbol, timestamp, period),
                timestamp,
                new TrendBarPrice(
                        new TrendBarPriceTime(timestamp, price),
                        new TrendBarPriceTime(timestamp, price),
                        price,
                        price
                )
        );
    }

    private TrendBar(TrendBarKey key, long timestamp, TrendBarPrice price) {
        this.key = Preconditions.checkNotNull(key);
        this.timestamp = timestamp;
        this.price = Preconditions.checkNotNull(price);
    }

    public TimeUnit getPeriod() {
        return key.getPeriod();
    }

    public String getSymbol() {
        return key.getSymbol();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public TrendBarPrice getPrice() {
        return price;
    }

    public TrendBarKey getKey() {
        return key;
    }

    public boolean isCompleted(long timestamp) {
        return key.isCompleted(timestamp);
    }

    public TrendBar add(TrendBar trendBar) {
        if (!key.equals(trendBar.getKey())) {
            return this;
        }

        return new TrendBar(key, timestamp, price.add(trendBar.price));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrendBar trendBar = (TrendBar) o;
        return timestamp == trendBar.timestamp &&
                Objects.equals(key, trendBar.key) &&
                Objects.equals(price, trendBar.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, timestamp, price);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("key", key)
                .add("timestamp", timestamp)
                .add("price", price)
                .toString();
    }

    public static TrendBar minuteOf(String symbol, long timestamp) {
        return new TrendBar(symbol, timestamp, TimeUnit.MINUTES, BigDecimal.ZERO);
    }

    public static TrendBar hourOf(String symbol, long timestamp) {
        return new TrendBar(symbol, timestamp, TimeUnit.HOURS, BigDecimal.ZERO);
    }

    public static TrendBar dayOf(String symbol, long timestamp) {
        return new TrendBar(symbol, timestamp, TimeUnit.DAYS, BigDecimal.ZERO);
    }

}
