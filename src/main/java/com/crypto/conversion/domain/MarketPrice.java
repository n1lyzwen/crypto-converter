package com.crypto.conversion.domain;

import java.math.BigDecimal;

public record MarketPrice(Market market, BigDecimal price) {

    public ConvertionPrice exchange(BigDecimal amount) {
        BigDecimal totalPrice = price.multiply(amount);
        return new ConvertionPrice(this, amount, totalPrice);
    }
}
