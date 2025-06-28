package com.crypto.conversion.infrastructure.message.conversion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ConvertTokenCommandErrorResultEvent {
    private String market;
    private BigDecimal amount;
    private UUID correlationId;
}
