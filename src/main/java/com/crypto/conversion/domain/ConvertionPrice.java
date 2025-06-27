package com.crypto.conversion.domain;

import java.math.BigDecimal;

public record ConvertionPrice(MarketPrice marketPrice, BigDecimal amount, BigDecimal totalPrice) {

    public String marketName() {
        return marketPrice.market().name();
    }
}
