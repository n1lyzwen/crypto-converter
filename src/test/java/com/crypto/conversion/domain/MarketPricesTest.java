package com.crypto.conversion.domain;


import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

class MarketPricesTest {

    @Test
    public void shouldFindMarketPriceWhenJumpingAvailable() {
        // Given
        // USDT/ETH = 4, ETH/BTC = 2, USDT/BTC = 8
        List<MarketPrice> markets = List.of(createMarketPrice("BTC/ETH", 0.5),
                createMarketPrice("ETH/USDT", 0.25));
        MarketPrices marketPrices = new MarketPrices(markets);

        // When
        Optional<MarketPrice> marketPrice = marketPrices.findMarketPrice(new Market("USDT/BTC"));

        // Then
        assertThat(marketPrice).isPresent();
        assertThat(marketPrice.map(MarketPrice::price).map(BigDecimal::doubleValue).get())
                .isEqualTo(8.0);
    }

    private MarketPrice createMarketPrice(String market, double price) {
        return new MarketPrice(new Market(market), BigDecimal.valueOf(price));
    }
}