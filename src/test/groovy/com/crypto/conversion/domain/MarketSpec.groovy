package com.crypto.conversion.domain

import spock.lang.Specification
import spock.lang.Unroll

class MarketSpec extends Specification {

    def "should parse valid market string"() {
        when:
        def market = new Market("BTC/USDT")

        then:
        market.base == "BTC"
        market.quote == "USDT"
        market.name() == "BTC/USDT"
    }

    @Unroll
    def "should throw for invalid market string"() {
        when:
        new Market(market)

        then:
        thrown(IllegalArgumentException)

        where:
        market << [null, "", "BTCUSDT", "BTC/USDT/ETH"]
    }

    @Unroll
    def "should throw for null base  or quote"() {
        when:
        new Market(base, quote)

        then:
        thrown(IllegalArgumentException)

        where:
        base  | quote
        null  | "USDT"
        "BTC" | null
        null  | null
    }

    def "should detect identical base and quote"() {
        expect:
        new Market("BTC/BTC").isIdentical()
    }

    def "should detect non-identical base and quote"() {
        expect:
        !new Market("BTC/USDT").isIdentical()
    }

}
