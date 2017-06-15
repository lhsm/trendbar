package com.github.lhsm.config;

import com.github.lhsm.trendbar.TrendBarMemoryStorage;
import com.github.lhsm.trendbar.TrendBarService;
import com.github.lhsm.trendbar.TrendBarServiceImpl;
import com.github.lhsm.trendbar.TrendBarStorage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.LinkedBlockingQueue;

@Configuration
public class TrendBarConfig {

    public static final int DEFAULT_BATCH_SIZE = 10_000;

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public TrendBarService trendBarService() {
        return new TrendBarServiceImpl(trendBarStorage(), new LinkedBlockingQueue<>(DEFAULT_BATCH_SIZE), Runtime.getRuntime().availableProcessors());
    }

    @Bean
    public TrendBarStorage trendBarStorage() {
        return new TrendBarMemoryStorage(DEFAULT_BATCH_SIZE);
    }

}
