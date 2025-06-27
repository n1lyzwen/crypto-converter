package com.crypto.conversion.infrastructure.message.marketprice;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class MarketPriceChangeEvent {

    private String market;
    private BigDecimal price;
    private UUID correlationId;

}
