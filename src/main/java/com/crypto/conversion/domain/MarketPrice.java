package com.crypto.conversion.domain;

import java.math.BigDecimal;

public record MarketPrice(Market market, BigDecimal price) {

    public MarketPrice {
        if (market == null || price == null || price.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Market and price must not be null or zero.");
        }
    }

    public ConversionPrice exchange(BigDecimal amount) {
        BigDecimal totalPrice = price.multiply(amount);
        return new ConversionPrice(this, amount, totalPrice);
    }
}
