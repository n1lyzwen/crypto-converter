package com.crypto.conversion.infrastructure.message.conversion;

import com.crypto.conversion.application.MarketPriceConversionPort;
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

    private final MarketPriceConversionPort marketPriceConversionPort;
    private final TokenConversionCommandResultEmitter resultEmitter;

    @KafkaHandler
    public void handleMarketPriceChangeEvent(ConvertTokenCommandEvent event) {
        log.info("Received MarketPriceChangeEvent {}", event);
        /*błędy możemy obsłużyć na dwa sposoby:
         1. zwracamy taki sam error jeżeli cokolwiek nie wyjdzie
         2. zwracamy konkretne komunikaty - wtedy optional nie będzie wystarczający
            i trzeba rzucać specyficzne wyjątki lub zwracać wynik razem z errorem, który będzie wypełniony tylko gdy coś nie wyjdzie
        */
        marketPriceConversionPort.convert(new Market(event.getMarket()), event.getAmount())
                .ifPresentOrElse(conversionPrice -> resultEmitter.emitConversionResult(conversionPrice, event.getCorrelationId()),
                        () -> resultEmitter.emitErrorResult(event));
    }

    @KafkaHandler(isDefault = true)
    public void defaultListener(@Payload Object event) {
        log.warn("Unsupported event: {}", event);
    }
}
