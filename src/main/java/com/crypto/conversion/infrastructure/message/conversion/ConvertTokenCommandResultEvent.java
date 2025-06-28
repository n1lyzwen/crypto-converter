package com.crypto.conversion.infrastructure.message.conversion;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@AllArgsConstructor
class ConvertTokenCommandResultEvent {
    private String market;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal totalPrice;
    private UUID correlationId;
}
