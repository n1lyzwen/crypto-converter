package com.crypto.conversion.domain;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class MarketPrices {

    private final Map<String, Map<String, BigDecimal>> currencies = new HashMap<>();

    public MarketPrices(List<MarketPrice> marketPrices) {
        for (MarketPrice price : marketPrices) {
            String base = price.market().getBase();
            String quote = price.market().getQuote();
            currencies.putIfAbsent(base, new HashMap<>());
            currencies.get(base).put(quote, price.price());
            currencies.putIfAbsent(quote, new HashMap<>());
            currencies.get(quote).put(base, BigDecimal.ONE.divide(price.price(), 2, RoundingMode.HALF_EVEN));
        }
    }

    public Optional<MarketPrice> findMarketPrice(Market market) {
        String base = market.getBase();
        String quote = market.getQuote();
        return getCurrencies(base).keySet().stream()
                .filter(getCurrencies(quote)::containsKey)
                .map(jumpToken -> getPriceForJumpToken(market, jumpToken))
                .map(price -> new MarketPrice(market, price))
                .findAny();
    }

    private Map<String, BigDecimal> getCurrencies(String currency) {
        return currencies.get(currency);
    }

    private BigDecimal getPriceForJumpToken(Market market, String jumpToken) {
        BigDecimal jumpBase = getCurrencies(market.getBase()).get(jumpToken);
        BigDecimal jumpQuote = getCurrencies(jumpToken).get(market.getQuote());
        return jumpBase.multiply(jumpQuote);
    }
}
