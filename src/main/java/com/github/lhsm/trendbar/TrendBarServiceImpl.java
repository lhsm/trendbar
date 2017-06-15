package com.github.lhsm.trendbar;

import com.google.common.base.Preconditions;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class TrendBarServiceImpl implements TrendBarService {

    public static final String THREAD_NAME_FORMAT = "trend-bar-worker-%d";

    private final static Logger LOGGER = LoggerFactory.getLogger(TrendBarServiceImpl.class);

    private final Queue<Quote> quotes;

    private final ExecutorService executorService;

    private final TrendBarStorage storage;

    private final int workerCount;

    public TrendBarServiceImpl(TrendBarStorage storage, Queue<Quote> quotes, int workerCount) {
        this.quotes = Preconditions.checkNotNull(quotes);
        this.executorService = Executors.newFixedThreadPool(workerCount, new ThreadFactoryBuilder().setNameFormat(THREAD_NAME_FORMAT).build());
        this.storage = Preconditions.checkNotNull(storage);
        this.workerCount = Preconditions.checkNotNull(workerCount);
    }

    @Override
    public void push(Collection<Quote> quotes) {
        this.quotes.addAll(quotes);
    }

    @Override
    public Stream<TrendBar> find(TrendBarFilter filter) {
        Preconditions.checkArgument(filter == null || SUPPORTED_PERIODS.contains(filter.getPeriod()), "Unsupported period");
        return storage.find(filter);
    }

    public void init() {
        for (int i = 0; i < workerCount; i++) {
            this.executorService.submit(new Worker(SUPPORTED_PERIODS));
        }

        LOGGER.info("Initialized {} workers", workerCount);
    }

    public void destroy() {
        executorService.shutdown();
        LOGGER.info("Workers destroyed");
    }

    private class Worker implements Runnable {

        private final Set<TimeUnit> periods;

        private Worker(Set<TimeUnit> periods) {
            this.periods = periods;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    Quote quote = quotes.poll();
                    if (quote != null) {
                        for (TimeUnit _period : periods) {
                            storage.save(new TrendBar(
                                    quote.getSymbol(),
                                    quote.getTime(),
                                    _period,
                                    quote.getPrice()
                            ));
                        }
                    }
                } catch (Exception e) {
                    LOGGER.error("Error occurred while handling quote", e);
                }
            }
        }
    }

    private static final Set<TimeUnit> SUPPORTED_PERIODS = EnumSet.of(TimeUnit.DAYS, TimeUnit.HOURS, TimeUnit.MINUTES);

}
