package com.crypto.conversion.application;

import com.crypto.conversion.domain.ConvertionPrice;
import com.crypto.conversion.domain.Market;
import com.crypto.conversion.domain.MarketPrice;
import com.crypto.conversion.domain.MarketPrices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
class MarketPriceConverter implements MarketPriceConvertionPort {

    private final MarketRepository marketRepository;

    @Override
    public ConvertionPrice convert(Market market, BigDecimal amount) {
        return marketRepository.findMarketPrice(market)
                .or(() -> composeMarketPrice(market))
                .map(marketPrice -> marketPrice.exchange(amount))
                .orElseThrow();
    }

    private Optional<MarketPrice> composeMarketPrice(Market market) {
        List<MarketPrice> baseMarketPrices = marketRepository.findMarketPrices(market.getBase());
        List<MarketPrice> quoteMarketPrices = marketRepository.findMarketPrices(market.getQuote());
        List<MarketPrice> concatedMarketPrices = Stream.concat(baseMarketPrices.stream(), quoteMarketPrices.stream()).toList();
        return new MarketPrices(concatedMarketPrices).findMarketPrice(market);
    }
}
