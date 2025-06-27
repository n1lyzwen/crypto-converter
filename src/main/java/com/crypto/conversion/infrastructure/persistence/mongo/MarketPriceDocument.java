package com.crypto.conversion.infrastructure.persistence.mongo;

import com.crypto.conversion.domain.Market;
import com.crypto.conversion.domain.MarketPrice;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document
class MarketPriceDocument {

    @Id
    private MarketId pairId;
    private String base;
    private String quote;
    private BigDecimal price;

    public static MarketPriceDocument from(MarketPrice marketPrice) {
        String base = marketPrice.market().getBase();
        String quote = marketPrice.market().getQuote();
        return new MarketPriceDocument(new MarketId(base, quote), base, quote, marketPrice.price());
    }

    public MarketPrice toMarketPrice() {
        return new MarketPrice(new Market(base, quote), price);
    }
}
