package com.crypto.conversion.application;

import com.crypto.conversion.domain.Market;
import com.crypto.conversion.domain.MarketPrice;

import java.util.List;
import java.util.Optional;

public interface MarketRepository {

    void removeMarketPrice(Market market);

    void updateMarketPrice(MarketPrice marketPrice);

    Optional<MarketPrice> findMarketPrice(Market market);

    List<MarketPrice> findMarketPrices(String token);

    void switchOff(Market market);

    void switchOn(Market market);

    Optional<Boolean> isMarketEnabled(Market market);
}
