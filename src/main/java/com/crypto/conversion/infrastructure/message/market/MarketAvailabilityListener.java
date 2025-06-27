package com.crypto.conversion.infrastructure.message.market;

import com.crypto.conversion.application.MarketManagementPort;
import com.crypto.conversion.domain.Market;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "${events.topics.market.availability}")
@RequiredArgsConstructor
class MarketAvailabilityListener {

    private final MarketManagementPort marketManagementPort;

    @KafkaHandler
    public void handleMarketPriceChangeEvent(MarketEnabledEvent event) {
        log.info("Received MarketEnabledEvent {}", event);
        marketManagementPort.enable(new Market(event.getMarket()));
    }

    @KafkaHandler
    public void handleMarketPriceChangeEvent(MarketDisabledEvent event) {
        log.info("Received MarketDisabledEvent {}", event);
        marketManagementPort.disable(new Market(event.getMarket()));
    }


    @KafkaHandler(isDefault = true)
    public void defaultListener(@Payload Object event) {
        log.warn("Unsupported event: {}", event);
    }

}
