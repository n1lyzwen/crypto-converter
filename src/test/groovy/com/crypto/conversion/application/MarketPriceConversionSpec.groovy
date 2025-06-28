package com.crypto.conversion.application

import com.crypto.conversion.domain.Market
import com.crypto.conversion.domain.MarketPrice
import spock.lang.Specification
import spock.lang.Unroll

class MarketPriceConversionSpec extends Specification {

    def marketRepository = Mock(MarketRepository)
    def marketManagementPort = Mock(MarketManagementPort)
    def converter = new MarketPriceConverter(marketRepository, marketManagementPort)

    def "should convert amount for enabled market with available market place in repository"() {
        given:
        def market = new Market("BTC/USDT")
        def price = 100G
        def amount = 2G
        def marketPrice = new MarketPrice(market, price)
        marketManagementPort.isEnabled(market) >> true
        marketRepository.findMarketPrice(market) >> Optional.of(marketPrice)

        when:
        def result = converter.convert(market, amount)

        then:
        result.present
        result.get().marketPrice == marketPrice
        result.get().amount == amount
        result.get().totalPrice == price * amount
    }

    def "should return empty when market is not enabled"() {
        given:
        def market = new Market("BTC/USDT")
        marketManagementPort.isEnabled(market) >> false

        when:
        def result = converter.convert(market, 1G)

        then:
        result.empty
    }

    @Unroll
    def "should return empty when illegal arguments"() {
        given:
        marketManagementPort.isEnabled(market) >> true

        expect:
        converter.convert(market, amount).empty

        where:
        market                 | amount
        new Market("BTC/USDT") | null
        new Market("BTC/USDT") | -1
        null                   | 1
    }
}
