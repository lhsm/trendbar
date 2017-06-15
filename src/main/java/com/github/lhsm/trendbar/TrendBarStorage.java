package com.github.lhsm.trendbar;

import java.util.stream.Stream;

public interface TrendBarStorage {

    void save(TrendBar trendBar);

    Stream<TrendBar> find(TrendBarFilter filter);

}
