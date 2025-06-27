package com.crypto.conversion.application;

import com.crypto.conversion.domain.Market;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class MarketAvailabilityEngine implements MarketManagementPort {

    private final MarketRepository marketRepository;

    @Override
    public void disable(Market market) {
        marketRepository.switchOff(market);
    }

    @Override
    public void enable(Market market) {
        marketRepository.switchOn(market);
    }
}
