package com.crypto.conversion.infrastructure.persistence.mongo

import com.crypto.conversion.domain.Market
import spock.lang.Specification
import spock.lang.Unroll

class MarketIdSpec extends Specification {

    @Unroll
    def "should compose marketId in alphabetical order"() {
        expect:
        new MarketId(base, quote).marketId == expected

        where:
        base   | quote  | expected
        "BTC"  | "USDT" | "BTC/USDT"
        "USDT" | "BTC"  | "BTC/USDT"
        "ETH"  | "BTC"  | "BTC/ETH"
        "BTC"  | "BTC"  | "BTC/BTC"
    }

    def "should create MarketId from Market with alphabetical order kept"() {
        given:
        def market = new Market("USDT/BTC");

        when:
        def marketId = new MarketId(market)

        then:
        marketId.marketId == "BTC/USDT"
    }

    @Unroll
    def "should throw when base or quote missing"() {
        when:
        new MarketId(base, quote)

        then:
        thrown(IllegalArgumentException)

        where:
        base  | quote
        null  | "USDT"
        "BTC" | null
        null  | null
    }
}
