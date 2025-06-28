package com.crypto.conversion.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;

public record MarketPrice(Market market, BigDecimal price) {

    public MarketPrice {
        if (market == null || price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Market and price must not be null or zero.");
        }
        price = rescale(price);
    }

    public ConversionPrice exchange(BigDecimal amount) {
        BigDecimal totalPrice = rescale(price.multiply(amount));
        return new ConversionPrice(this, amount, totalPrice);
    }

    private BigDecimal rescale(BigDecimal value) {
        return value.setScale(4, RoundingMode.HALF_EVEN);
    }
}
