package com.crypto.conversion.domain

import spock.lang.Specification

class MarketPricesSpec extends Specification {

    def "should find market price when jumping available"() {
        given:
        // USDT/ETH = 4, ETH/BTC = 2, USDT/BTC = 8
        def markets = [
                createMarketPrice("BTC/ETH", 0.5),
                createMarketPrice("ETH/USDT", 0.25)
        ]
        def marketPrices = new MarketPrices(markets)

        when:
        def marketPrice = marketPrices.findMarketPrice(new Market("USDT/BTC"))

        then:
        marketPrice.present
        marketPrice.get().price == 8G
        marketPrice.get().market.name() == "USDT/BTC"
    }

    def "should return one when token is the same with no matter if markets exist"() {
        given:
        def marketPrices = new MarketPrices([])

        when:
        def marketPrice = marketPrices.findMarketPrice(new Market("USDT/USDT"))

        then:
        marketPrice.present
        marketPrice.get().price == 1G
        marketPrice.get().market.name() == "USDT/USDT"
    }

    def "should be empty when no connection found"() {
        given:
        def markets = [
                createMarketPrice("BTC/ETH", 0.5),
                createMarketPrice("ETH/USDT", 0.25)
        ]
        def marketPrices = new MarketPrices(markets)

        when:
        def marketPrice = marketPrices.findMarketPrice(new Market("ETH/ABC"))

        then:
        !marketPrice.present
    }

    def "should return exact price when market price is directly given"() {
        given:
        def markets = [
                createMarketPrice("BTC/ETH", 0.5),
                createMarketPrice("ETH/USDT", 0.25)
        ]
        def marketPrices = new MarketPrices(markets)

        when:
        def marketPrice = marketPrices.findMarketPrice(new Market("USDT/ETH"))

        then:
        marketPrice.present
        marketPrice.get().price == 4G
        marketPrice.get().market.name() == "USDT/ETH"
    }

    private MarketPrice createMarketPrice(String market, double price) {
        new MarketPrice(new Market(market), price as BigDecimal)
    }
}
