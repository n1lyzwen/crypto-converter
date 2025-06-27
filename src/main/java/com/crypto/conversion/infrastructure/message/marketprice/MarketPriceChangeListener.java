package com.crypto.conversion.infrastructure.message.marketprice;

import com.crypto.conversion.application.MarketPriceUpdaterPort;
import com.crypto.conversion.domain.Market;
import com.crypto.conversion.domain.MarketPrice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Component
@KafkaListener(topics = "${events.topics.market.pricing}")
@RequiredArgsConstructor
class MarketPriceChangeListener {

    private final MarketPriceUpdaterPort marketPriceUpdaterPort;
    private final Set<UUID> processedEventIds = ConcurrentHashMap.newKeySet();

    @KafkaHandler
    public void handleMarketPriceChangeEvent(MarketPriceChangeEvent event) {
        log.info("Received MarketPriceChangeEvent {}", event);
        // Mechanizm deduplikacji tylko dla wskazania miejsca, gdzie to można zrobić. Realnie state powinien być w jakiejś bazce, np Redis
        UUID eventId = event.getCorrelationId();
        if (!processedEventIds.add(eventId)) {
            log.info("Duplicate event detected: {}", eventId);
            return;
        }
        marketPriceUpdaterPort.update(getMarketPrice(event));
    }

    @KafkaHandler(isDefault = true)
    public void defaultListener(@Payload Object event) {
        log.warn("Unsupported event: {}", event);
    }

    private static MarketPrice getMarketPrice(MarketPriceChangeEvent event) {
        Market market = new Market(event.getMarket());
        return new MarketPrice(market, event.getPrice());
    }

}
