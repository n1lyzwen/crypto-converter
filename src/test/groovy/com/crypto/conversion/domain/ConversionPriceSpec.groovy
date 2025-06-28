package com.crypto.conversion.domain

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class ConversionPriceSpec extends Specification {

    @Shared
    def static market = new Market("BTC/USDT")
    @Shared
    def static marketPrice = new MarketPrice(market, 100G)

    @Unroll
    def "should throw for invalid parameters"() {
        when:
        new ConversionPrice(marketPrice, amount, totalPrice)

        then:
        thrown(IllegalArgumentException)

        where:
        marketPrice | amount | totalPrice
        null        | 1G     | 1G
        marketPrice | null   | 1G
        marketPrice | 1G     | null
        marketPrice | -1G    | 1G
        marketPrice | 1G     | -1G
        null        | null   | null
    }

    def "should create for valid args"() {
        when:
        def cp = new ConversionPrice(marketPrice, 2G, 200G)

        then:
        cp.marketPrice.market.name() == "BTC/USDT"
        cp.amount == 2G
        cp.totalPrice == 200G
    }

    def "should return correct market name"() {
        given:
        def cp = new ConversionPrice(marketPrice, 2G, 200G)

        expect:
        cp.marketName() == "BTC/USDT"
    }
}
