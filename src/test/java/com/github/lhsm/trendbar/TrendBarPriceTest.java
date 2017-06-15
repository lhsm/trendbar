package com.github.lhsm.trendbar;


import org.junit.Assert;
import org.junit.Test;

import java.math.BigDecimal;

public class TrendBarPriceTest {

    private TrendBarPrice defaultPrice = TrendBarPrice.of(1_000, BigDecimal.valueOf(1.0));

    @Test
    public void shouldUpdateClosePrice() {
        TrendBarPrice price = TrendBarPrice.of(1_001, BigDecimal.valueOf(1.1));

        TrendBarPrice updatedPrice = defaultPrice.add(price);

        Assert.assertEquals(price.getClose(), updatedPrice.getClose());
    }

    @Test
    public void shouldNotUpdateClosePrice() {
        TrendBarPrice price = TrendBarPrice.of(999, BigDecimal.valueOf(1.1));

        TrendBarPrice updatedPrice = defaultPrice.add(price);

        Assert.assertEquals(defaultPrice.getClose(), updatedPrice.getClose());
    }

    @Test
    public void shouldUpdateOpenPrice() {
        TrendBarPrice price = TrendBarPrice.of(999, BigDecimal.valueOf(1.1));

        TrendBarPrice updatedPrice = defaultPrice.add(price);

        Assert.assertEquals(price.getOpen(), updatedPrice.getOpen());
    }

    @Test
    public void shouldNotUpdateOpenPrice() {
        TrendBarPrice price = TrendBarPrice.of(1_001, BigDecimal.valueOf(1.1));

        TrendBarPrice updatedPrice = defaultPrice.add(price);

        Assert.assertEquals(defaultPrice.getOpen(), updatedPrice.getOpen());
    }

    @Test
    public void shouldUpdateHighPrice() {
        TrendBarPrice price = TrendBarPrice.of(1_000, BigDecimal.valueOf(1.1));

        TrendBarPrice updatedPrice = defaultPrice.add(price);

        Assert.assertEquals(price.getHigh(), updatedPrice.getHigh());
    }

    @Test
    public void shouldNotUpdateHighPrice() {
        TrendBarPrice price = TrendBarPrice.of(1_000, BigDecimal.valueOf(0.9));

        TrendBarPrice updatedPrice = defaultPrice.add(price);

        Assert.assertEquals(defaultPrice.getHigh(), updatedPrice.getHigh());
    }

    @Test
    public void shouldUpdateLowPrice() {
        TrendBarPrice price = TrendBarPrice.of(1_000, BigDecimal.valueOf(0.9));

        TrendBarPrice updatedPrice = defaultPrice.add(price);

        Assert.assertEquals(price.getLow(), updatedPrice.getLow());
    }

    @Test
    public void shouldNotUpdateLowPrice() {
        TrendBarPrice price = TrendBarPrice.of(1_000, BigDecimal.valueOf(1.1));

        TrendBarPrice updatedPrice = defaultPrice.add(price);

        Assert.assertEquals(defaultPrice.getLow(), updatedPrice.getLow());
    }

}