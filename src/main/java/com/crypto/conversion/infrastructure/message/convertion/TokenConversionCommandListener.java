package com.crypto.conversion.infrastructure.message.convertion;

import com.crypto.conversion.application.MarketPriceConvertionPort;
import com.crypto.conversion.domain.ConvertionPrice;
import com.crypto.conversion.domain.Market;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@KafkaListener(topics = "${events.topics.market.conversion}")
@RequiredArgsConstructor
class TokenConversionCommandListener {

    private final MarketPriceConvertionPort marketPriceConvertionPort;
    private final TokenConvertionCommandResultEmitter resultEmitter;

    @KafkaHandler
    public void handleMarketPriceChangeEvent(ConvertTokenCommandEvent event) {
        log.info("Received MarketPriceChangeEvent {}", event);
        ConvertionPrice convertionPrice = marketPriceConvertionPort.convert(new Market(event.getMarket()), event.getAmount());
        resultEmitter.emitConvertionResult(convertionPrice, event.getCorrelationId());
    }

    @KafkaHandler(isDefault = true)
    public void defaultListener(@Payload Object event) {
        log.warn("Unsupported event: {}", event);
    }
}
