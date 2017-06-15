package com.github.lhsm.trendbar;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

import java.util.concurrent.TimeUnit;

public class TrendBarFilter {

    private final String symbol;

    private final TimeUnit period;

    private final long from;

    private final long to;

    public TrendBarFilter(String symbol, TimeUnit period, long from, long to) {
        this.symbol = Preconditions.checkNotNull(symbol);
        this.period = Preconditions.checkNotNull(period);
        this.from = from;
        this.to = to;
    }

    public String getSymbol() {
        return symbol;
    }

    public TimeUnit getPeriod() {
        return period;
    }

    public long getFrom() {
        return from;
    }

    public long getTo() {
        return to;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("symbol", symbol)
                .add("period", period)
                .add("from", from)
                .add("to", to)
                .toString();
    }

}
