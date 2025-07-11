package com.crypto.conversion.infrastructure.persistence.mongo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
class MarketAvailabilityDocument {

    @Id
    private MarketId marketId;
    private boolean enabled;
}
