package com.crypto.conversion.domain;

import lombok.Getter;

import java.util.Optional;

@Getter
public class Market {

    private static final String MARKET_NAME_FORMAT = "%s/%s";
    private final String base;
    private final String quote;

    public Market(String market) {
        String[] parts = Optional.ofNullable(market)
                .map(split -> split.split("/"))
                .filter(split -> split.length == 2)
                .orElseThrow(() -> {
                    throw new IllegalArgumentException("Invalid marketPrice format. Expected format: base/quote");
                });
        base = parts[0].trim();
        quote = parts[1].trim();
    }

    public Market(String base, String quote) {
        if (base == null || quote == null) {
            throw new IllegalArgumentException("Base and quote tokens must not be null");
        }
        this.base = base.trim();
        this.quote = quote.trim();
    }

    public String name() {
        return String.format(MARKET_NAME_FORMAT, base, quote);
    }

    public boolean isIdentical() {
        return base.equals(quote);
    }
}
