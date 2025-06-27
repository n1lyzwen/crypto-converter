package com.crypto.conversion.infrastructure.persistence.mongo;

import com.crypto.conversion.domain.Market;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
class MarketId {

    private static final String MARKET_ID_FORMAT = "%s/%s";
    private String marketId;

    public MarketId(String id) {
        this.marketId = id;
    }

    public MarketId(Market market) {
        this(market.getBase(), market.getQuote());
    }

    public MarketId(String base, String quote) {
        marketId = composeMarketId(base, quote);
    }

    private String composeMarketId(String base, String quote) {
        String firstInAlphabeticalOrder = base;
        String secondInAlphabeticalOrder = quote;
        if (base.compareTo(quote) > 0) {
            firstInAlphabeticalOrder = quote;
            secondInAlphabeticalOrder = base;
        }
        return String.format(MARKET_ID_FORMAT, firstInAlphabeticalOrder, secondInAlphabeticalOrder);
    }
}
