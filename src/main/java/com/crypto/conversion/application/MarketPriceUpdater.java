package com.crypto.conversion.application;

import com.crypto.conversion.domain.Market;
import com.crypto.conversion.domain.MarketPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
class MarketPriceUpdater implements MarketPriceUpdaterPort {

    private final MarketRepository marketRepository;
    private final MarketManagementPort marketManagementPort;

    @Override
    public void update(MarketPrice marketPrice) {
        Market market = marketPrice.market();
        if (marketManagementPort.isDisabled(market)) {
            log.info("Skipping market price update because Market {} is disabled", market);
            return;
        }
        marketRepository.updateMarketPrice(marketPrice);
    }
}
