package com.github.lhsm.trendbar;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.util.Objects;

public class TrendBarPriceTime {

    private final BigDecimal price;

    private final long timestamp;

    public TrendBarPriceTime(long timestamp, BigDecimal price) {
        this.price = Preconditions.checkNotNull(price);
        this.timestamp = timestamp;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getTimestamp() {
        return timestamp;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrendBarPriceTime that = (TrendBarPriceTime) o;
        return timestamp == that.timestamp &&
                Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(price, timestamp);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("price", price)
                .add("timestamp", timestamp)
                .toString();
    }

    public static TrendBarPriceTime of(long timestamp) {
        return new TrendBarPriceTime(timestamp, BigDecimal.ZERO);
    }

}
