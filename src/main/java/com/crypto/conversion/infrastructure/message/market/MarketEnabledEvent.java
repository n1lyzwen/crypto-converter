package com.crypto.conversion.infrastructure.message.market;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
class MarketEnabledEvent {
    private String market;
}
