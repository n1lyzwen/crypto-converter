package com.crypto.conversion.application;

import com.crypto.conversion.domain.MarketPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MarketPriceUpdater implements MarketPriceUpdaterPort {

    private final MarketRepository marketRepository;

    @Override
    public void update(MarketPrice marketPrice) {
        marketRepository.updateMarketPrice(marketPrice);
    }
}
