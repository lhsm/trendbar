package com.github.lhsm.trendbar;

import java.time.LocalDateTime;
import java.util.Collection;

public interface QuotesProvider {

    Collection<Quote> generateYesterday(int count, float price, float diffusion, String... symbols);

    Collection<Quote> generate(int count, float price, float diffusion, LocalDateTime from, LocalDateTime to, String... symbols);

}
