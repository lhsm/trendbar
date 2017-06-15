package com.github.lhsm.trendbar;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public class TrendBarMemoryStorage implements TrendBarStorage {

    private final ConcurrentHashMap<TrendBarKey, TrendBar> store;

    public TrendBarMemoryStorage(int initialCapacity) {
        this.store = new ConcurrentHashMap<>(initialCapacity);
    }

    @Override
    public void save(TrendBar trendBar) {
        TrendBarKey key = new TrendBarKey(trendBar.getSymbol(), trendBar.getTimestamp(), trendBar.getPeriod());
        store.putIfAbsent(key, trendBar);
        store.computeIfPresent(key, (trendBarKey, trendBar1) -> store.get(trendBarKey).add(trendBar));
    }

    @Override
    public Stream<TrendBar> find(TrendBarFilter filter) {
        return store.values().stream().filter(trendBar -> test(trendBar.getKey(), filter));
    }

    private boolean test(TrendBarKey key, TrendBarFilter filter) {
        return filter == null
                || Objects.equals(key.getSymbol(), filter.getSymbol())
                && key.getPeriod() == filter.getPeriod()
                && key.getStart() >= filter.getFrom()
                && key.getStart() <= filter.getTo()
                && key.isCompleted(System.currentTimeMillis());
    }

}
