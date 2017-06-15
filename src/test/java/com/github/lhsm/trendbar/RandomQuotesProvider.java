package com.github.lhsm.trendbar;

import io.github.benas.randombeans.EnhancedRandomBuilder;
import io.github.benas.randombeans.api.EnhancedRandom;
import io.github.benas.randombeans.api.Randomizer;
import io.github.benas.randombeans.randomizers.range.IntegerRangeRandomizer;
import io.github.benas.randombeans.randomizers.range.LocalDateTimeRangeRandomizer;
import io.github.benas.randombeans.randomizers.range.LongRangeRandomizer;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.Collectors;

public class RandomQuotesProvider implements QuotesProvider {

    public static final int DEFAULT_SCALE = 4;

    private static final long DEFAULT_SEED = 123L;

    @Override
    public Collection<Quote> generateYesterday(int count, float price, float diffusion, String... symbols) {
        LocalDateTime midnight = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);

        return generate(count, price, diffusion, midnight.minusDays(1), midnight.minusMinutes(1), symbols);
    }

    @Override
    public Collection<Quote> generate(int count, float price, float diffusion, LocalDateTime from, LocalDateTime to, String... symbols) {
        EnhancedRandom random = EnhancedRandomBuilder.aNewEnhancedRandomBuilder()
                .seed(DEFAULT_SEED)
                .randomize(BigDecimal.class, new PriceRandomizer(
                        DEFAULT_SCALE,
                        getLongPrice(price) - getLongPrice(diffusion),
                        getLongPrice(price) + getLongPrice(diffusion),
                        DEFAULT_SEED
                ))
                .randomize(Long.class, new TimeRandomizer(from, to, DEFAULT_SEED))
                .randomize(String.class, new NameRandomizer(DEFAULT_SEED, symbols))
                .build();

        return random.objects(Quote.class, count).collect(Collectors.toList());
    }

    private long getLongPrice(float price) {
        return (long) (price * Math.pow(10, DEFAULT_SCALE));
    }

    public static class NameRandomizer implements Randomizer<String> {

        private final String[] names;

        private final Randomizer<Integer> source;

        public NameRandomizer(long seed, String[] names) {
            this.source = new IntegerRangeRandomizer(0, names.length, seed);
            this.names = names;
        }

        @Override
        public String getRandomValue() {
            return names[source.getRandomValue()];
        }

    }

    public static class PriceRandomizer implements Randomizer<BigDecimal> {

        private final int scale;

        private final Randomizer<Long> source;

        public PriceRandomizer(int scale, long min, long max, long seed) {
            this.scale = scale;
            this.source = new LongRangeRandomizer(min, max, seed);
        }

        @Override
        public BigDecimal getRandomValue() {
            return new BigDecimal(BigInteger.valueOf(source.getRandomValue()), scale);
        }

    }

    public static class TimeRandomizer implements Randomizer<Long> {

        private final Randomizer<LocalDateTime> source;

        public TimeRandomizer(LocalDateTime min, LocalDateTime max, long seed) {
            this.source = new LocalDateTimeRangeRandomizer(min, max, seed);
        }

        @Override
        public Long getRandomValue() {
            LocalDateTime time = source.getRandomValue();
            return Timestamp.from(time.toInstant(ZoneOffset.UTC)).getTime();
        }

    }
}
