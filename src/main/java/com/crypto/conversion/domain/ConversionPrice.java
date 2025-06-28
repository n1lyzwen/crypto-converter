package com.crypto.conversion.domain;

import java.math.BigDecimal;

public record ConversionPrice(MarketPrice marketPrice, BigDecimal amount, BigDecimal totalPrice) {

    public ConversionPrice {
        if (marketPrice == null || amount == null || totalPrice == null ||
                amount.compareTo(BigDecimal.ZERO) < 0 || totalPrice.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("MarketPrice, amount, and totalPrice must not be null or zero.");
        }
    }

    public String marketName() {
        return marketPrice.market().name();
    }
}
