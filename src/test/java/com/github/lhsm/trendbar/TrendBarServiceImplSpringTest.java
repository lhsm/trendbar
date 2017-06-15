package com.github.lhsm.trendbar;

import com.github.lhsm.config.TrendBarTestConfig;
import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {TrendBarTestConfig.class})
public class TrendBarServiceImplSpringTest {

    @Autowired
    private TrendBarService service;

    @Autowired
    private QuotesProvider provider;

    @Test
    public void test() throws Exception {
        service.push(provider.generateYesterday(10, 123F, 1F, "EURJPY"));
        service.push(provider.generateYesterday(10, 1.27F, 0.01F, "GBPUSD"));
        service.push(provider.generateYesterday(10, 1.12F, 0.01F, "EURUSD"));

        Thread.sleep(1_000);

        Assert.assertTrue(service.find(null).collect(Collectors.toList()).size() > 0);
        assertSize("EURJPY", TimeUnit.MINUTES, 10);
        assertSize("EURJPY", TimeUnit.HOURS, 8);
        assertSize("EURJPY", TimeUnit.DAYS, 1);
        assertSize("GBPUSD", TimeUnit.MINUTES, 10);
        assertSize("GBPUSD", TimeUnit.HOURS, 8);
        assertSize("GBPUSD", TimeUnit.DAYS, 1);
        assertSize("EURUSD", TimeUnit.MINUTES, 10);
        assertSize("EURUSD", TimeUnit.HOURS, 8);
        assertSize("EURUSD", TimeUnit.DAYS, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void unsupportedPeriod() throws Exception {
        service.find(new TrendBarFilter("any", TimeUnit.SECONDS, 0, 0));
    }

    private void assertSize(String symbol, TimeUnit period, int size) {
        Assert.assertThat(service.find(new TrendBarFilter(
                symbol,
                period,
                Timestamp.from(LocalDateTime.now().minusDays(2).toInstant(ZoneOffset.UTC)).getTime(),
                Timestamp.from(LocalDateTime.now().toInstant(ZoneOffset.UTC)).getTime()
        )).collect(Collectors.toList()).size(), CoreMatchers.is(size));
    }

}