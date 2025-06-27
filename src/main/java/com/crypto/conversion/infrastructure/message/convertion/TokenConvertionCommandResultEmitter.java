package com.crypto.conversion.infrastructure.message.convertion;

import com.crypto.conversion.domain.ConvertionPrice;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
class TokenConvertionCommandResultEmitter {

    private final String resultTopic;
    private final KafkaTemplate<UUID, ConvertTokenCommandResultEvent> kafkaTemplate;

    public TokenConvertionCommandResultEmitter(@Value("${events.topics.market.conversion-result}") String resultTopic,
                                               KafkaTemplate<UUID, ConvertTokenCommandResultEvent> kafkaTemplate) {
        this.resultTopic = resultTopic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void emitConvertionResult(ConvertionPrice convertionPrice, UUID correlationKey) {
        ConvertTokenCommandResultEvent event = mapToResultEvent(convertionPrice);
        kafkaTemplate.send(resultTopic, correlationKey, event)
                .whenComplete((recordId, exception) -> {
                    // Dla uproszczenia po prostu logowanie albo wyjątek
                    if (exception != null) {
                        throw new RuntimeException(exception);
                    } else {
                        System.out.println("Conversion result sent successfully: " + recordId);
                    }
                });
    }

    private static ConvertTokenCommandResultEvent mapToResultEvent(ConvertionPrice convertionPrice) {
        return new ConvertTokenCommandResultEvent(
                convertionPrice.marketName(),
                convertionPrice.marketPrice().price(),
                convertionPrice.amount(),
                convertionPrice.totalPrice()
        );
    }
}
