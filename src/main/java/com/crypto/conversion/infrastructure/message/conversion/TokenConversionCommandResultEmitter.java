package com.crypto.conversion.infrastructure.message.conversion;

import com.crypto.conversion.domain.ConversionPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class TokenConversionCommandResultEmitter {

    private final String resultTopic;
    private final KafkaTemplate<UUID, Object> kafkaResultTemplate;

    public TokenConversionCommandResultEmitter(@Value("${events.topics.market.conversion-result}") String resultTopic,
                                               KafkaTemplate<UUID, Object> kafkaResultTemplate) {
        this.resultTopic = resultTopic;
        this.kafkaResultTemplate = kafkaResultTemplate;
    }

    public void emitConversionResult(ConversionPrice conversionPrice, UUID correlationKey) {
        ConvertTokenCommandResultEvent event = mapToResultEvent(conversionPrice, correlationKey);
        kafkaResultTemplate.send(resultTopic, correlationKey, event)
                .whenComplete((recordId, exception) -> {
                    // Dla uproszczenia po prostu logowanie albo wyjątek
                    if (exception != null) {
                        throw new RuntimeException(exception);
                    } else {
                        System.out.println("Conversion result sent successfully: " + recordId);
                    }
                });
    }

    public void emitErrorResult(ConvertTokenCommandEvent event) {
        UUID correlationId = event.getCorrelationId();
        ConvertTokenCommandErrorResultEvent errorResultEvent =
                new ConvertTokenCommandErrorResultEvent(event.getMarket(), event.getAmount(), correlationId);
        kafkaResultTemplate.send(resultTopic, correlationId, errorResultEvent)
                .whenComplete((recordId, exception) -> {
                    // Dla uproszczenia po prostu logowanie albo wyjątek
                    if (exception != null) {
                        throw new RuntimeException(exception);
                    } else {
                        System.out.println("Conversion error result sent successfully: " + recordId);
                    }
                });
    }

    private static ConvertTokenCommandResultEvent mapToResultEvent(ConversionPrice conversionPrice, UUID correlationId) {
        return new ConvertTokenCommandResultEvent(
                conversionPrice.marketName(),
                conversionPrice.marketPrice().price(),
                conversionPrice.amount(),
                conversionPrice.totalPrice(),
                correlationId
        );
    }
}
