package com.crypto.conversion.application;

import com.crypto.conversion.domain.Market;
import com.crypto.conversion.domain.MarketPrice;

import java.util.List;
import java.util.Optional;

public interface MarketRepository {

    void updateMarketPrice(MarketPrice marketPrice);

    Optional<MarketPrice> findMarketPrice(Market market);

    List<MarketPrice> findMarketPrices(String currency);

    void switchOff(Market market);

    void switchOn(Market market);

    Optional<Boolean> isMarketEnabled(Market market);
}
