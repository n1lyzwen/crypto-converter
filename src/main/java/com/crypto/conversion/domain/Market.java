package com.crypto.conversion.domain;

import lombok.Getter;

import java.util.Objects;
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
        base = Objects.requireNonNull(parts[0]);
        quote = Objects.requireNonNull(parts[1]);
    }

    public Market(String base, String quote) {
        this.base = Objects.requireNonNull(base);
        this.quote = Objects.requireNonNull(quote);
    }

    public String name() {
        return String.format(MARKET_NAME_FORMAT, base, quote);
    }
}
