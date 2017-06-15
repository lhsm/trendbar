package com.github.lhsm.config;

import com.github.lhsm.trendbar.QuotesProvider;
import com.github.lhsm.trendbar.RandomQuotesProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TrendBarConfig.class)
public class TrendBarTestConfig {

    @Bean
    public QuotesProvider quotesProvider() {
        return new RandomQuotesProvider();
    }

}
