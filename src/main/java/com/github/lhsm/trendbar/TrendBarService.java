package com.github.lhsm.trendbar;

import java.util.Collection;
import java.util.stream.Stream;

public interface TrendBarService {

    void push(Collection<Quote> quotes);

    Stream<TrendBar> find(TrendBarFilter filter);

}
