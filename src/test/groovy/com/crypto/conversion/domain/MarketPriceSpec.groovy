package com.crypto.conversion.domain

import spock.lang.Specification
import spock.lang.Unroll

class MarketPriceSpec extends Specification {

    @Unroll
    def "should throw for invalid market or price"() {
        when:
        new MarketPrice(market, price)

        then:
        thrown(IllegalArgumentException)

        where:
        market                 | price
        null                   | 1G
        new Market("BTC/USDT") | null
        new Market("BTC/USDT") | 0G
        new Market("BTC/USDT") | -1G
        null                   | null
    }

    def "should create MarketPrice for valid args"() {
        given:
        def market = new Market("BTC/USDT")

        when:
        def marketPrice = new MarketPrice(market, 100G)

        then:
        marketPrice.market.name() == "BTC/USDT"
        marketPrice.price == 100G
    }

    def "should calculate exchange price correctly"() {
        given:
        def market = new Market("BTC/USDT")
        def marketPrice = new MarketPrice(market, 2.5G)

        when:
        def result = marketPrice.exchange(4G)

        then:
        result.amount == 4G
        result.totalPrice == 10G
        result.marketPrice.market.name() == "BTC/USDT"
    }

    def "should rescale price down"() {
        given:
        def market = new Market("BTC/USDT")
        def marketPrice = new MarketPrice(market, 2.52345)

        expect:
        marketPrice.price == 2.5234
    }

    def "should rescale price up"() {
        given:
        def market = new Market("BTC/USDT")
        def marketPrice = new MarketPrice(market, 2.52346)

        expect:
        marketPrice.price == 2.5235
    }
}
