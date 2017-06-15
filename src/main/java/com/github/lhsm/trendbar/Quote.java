package com.github.lhsm.trendbar;

import com.google.common.base.MoreObjects;

import java.math.BigDecimal;

public class Quote {

    private final String symbol;

    private final BigDecimal price;

    private final long time;

    public Quote(String symbol, BigDecimal price, long time) {
        this.symbol = symbol;
        this.price = price;
        this.time = time;
    }

    public String getSymbol() {
        return symbol;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public long getTime() {
        return time;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("symbol", symbol)
                .add("price", price)
                .add("time", time)
                .toString();
    }
}
