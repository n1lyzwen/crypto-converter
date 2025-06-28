package com.crypto.conversion.application;

import com.crypto.conversion.domain.Market;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
class MarketAvailabilityEngine implements MarketManagementPort {

    private final MarketRepository marketRepository;

    @Override
    @Transactional
    public void disable(Market market) {
        marketRepository.switchOff(market);
        marketRepository.removeMarketPrice(market);
        log.info("Market {} has been disabled", market);
    }

    @Override
    public void enable(Market market) {
        marketRepository.switchOn(market);
        log.info("Market {} has been enabled", market);
    }

    @Override
    public boolean isDisabled(Market market) {
        return !isEnabled(market);
    }

    @Override
    public boolean isEnabled(Market market) {
        Boolean enabled = marketRepository.isMarketEnabled(market)
                .orElse(true);
        log.info("Checking if market {} is enabled: {}", market, enabled);
        return enabled;
    }
}
