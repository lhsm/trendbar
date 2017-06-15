package com.github.lhsm.trendbar;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.math.BigDecimal;
import java.util.Objects;

public class TrendBarPrice {

    private final TrendBarPriceTime open;

    private final TrendBarPriceTime close;

    private final BigDecimal low;

    private final BigDecimal high;

    public TrendBarPrice(TrendBarPriceTime open, TrendBarPriceTime close, BigDecimal low, BigDecimal high) {
        this.open = Preconditions.checkNotNull(open);
        this.close = Preconditions.checkNotNull(close);
        this.low = Preconditions.checkNotNull(low);
        this.high = Preconditions.checkNotNull(high);
    }

    public BigDecimal getOpen() {
        return open.getPrice();
    }

    public BigDecimal getClose() {
        return close.getPrice();
    }

    public BigDecimal getLow() {
        return low;
    }

    public BigDecimal getHigh() {
        return high;
    }

    public TrendBarPrice add(TrendBarPrice price) {
        return new TrendBarPrice(
                price.open.getTimestamp() < open.getTimestamp() ? price.open : open,
                price.close.getTimestamp() > close.getTimestamp() ? price.close : close,
                low.min(price.low),
                high.max(price.high)

        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TrendBarPrice that = (TrendBarPrice) o;
        return Objects.equals(open, that.open) &&
                Objects.equals(close, that.close) &&
                Objects.equals(low, that.low) &&
                Objects.equals(high, that.high);
    }

    @Override
    public int hashCode() {
        return Objects.hash(open, close, low, high);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("open", open)
                .add("close", close)
                .add("low", low)
                .add("high", high)
                .toString();
    }

    public static TrendBarPrice of(long timestamp) {
        return new TrendBarPrice(
                TrendBarPriceTime.of(timestamp),
                TrendBarPriceTime.of(timestamp),
                BigDecimal.ZERO,
                BigDecimal.ZERO
        );
    }

    public static TrendBarPrice of(long timestamp, BigDecimal price) {
        return new TrendBarPrice(
                new TrendBarPriceTime(timestamp, price),
                new TrendBarPriceTime(timestamp, price),
                price,
                price
        );
    }

}
