package com.crypto.conversion.infrastructure.message.convertion;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class ConvertTokenCommandResultEvent {
    private String market;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal totalValue;
}
