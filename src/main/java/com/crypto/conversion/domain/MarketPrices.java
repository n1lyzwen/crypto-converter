package com.crypto.conversion.domain;

import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class MarketPrices {

    private final Map<String, Map<String, BigDecimal>> tokens = new HashMap<>();

    public MarketPrices(List<MarketPrice> marketPrices) {
        for (MarketPrice price : marketPrices) {
            String base = price.market().getBase();
            String quote = price.market().getQuote();
            tokens.putIfAbsent(base, new HashMap<>());
            tokens.get(base).put(quote, price.price());
            tokens.putIfAbsent(quote, new HashMap<>());
            tokens.get(quote).put(base, BigDecimal.ONE.divide(price.price(), 2, RoundingMode.HALF_EVEN));
        }
    }

    public Optional<MarketPrice> findMarketPrice(Market market) {
        if (market.isIdentical()) {
            return Optional.of(new MarketPrice(market, BigDecimal.ONE));
        }
        String base = market.getBase();
        String quote = market.getQuote();
        if (hasDirectPrice(market)) {
            BigDecimal price = getTokensFor(base).get(quote);
            return Optional.of(new MarketPrice(market, price));
        }
        return getTokensFor(base).keySet().stream()
                .filter(getTokensFor(quote)::containsKey)
                .map(jumpToken -> getPriceForJumpToken(market, jumpToken))
                .map(price -> new MarketPrice(market, price))
                .findAny();
    }

    private boolean hasDirectPrice(Market market) {
        return getTokensFor(market.getBase()).containsKey(market.getQuote());
    }

    private Map<String, BigDecimal> getTokensFor(String token) {
        return tokens.getOrDefault(token, new HashMap<>());
    }

    private BigDecimal getPriceForJumpToken(Market market, String jumpToken) {
        log.info("Getting price by jump token {}", jumpToken);
        BigDecimal jumpBase = getTokensFor(market.getBase()).get(jumpToken);
        BigDecimal jumpQuote = getTokensFor(jumpToken).get(market.getQuote());
        return jumpBase.multiply(jumpQuote);
    }
}
