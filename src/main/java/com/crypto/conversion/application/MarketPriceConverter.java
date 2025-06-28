package com.crypto.conversion.application;

import com.crypto.conversion.domain.ConversionPrice;
import com.crypto.conversion.domain.Market;
import com.crypto.conversion.domain.MarketPrice;
import com.crypto.conversion.domain.MarketPrices;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Slf4j
@Component
@RequiredArgsConstructor
class MarketPriceConverter implements MarketPriceConversionPort {

    private final MarketRepository marketRepository;
    private final MarketManagementPort marketManagementPort;

    @Override
    public Optional<ConversionPrice> convert(Market market, BigDecimal amount) {
        if (!isConversionPossible(market, amount)) {
            log.info("Cannot convert amount {} for market {}", amount, market);
            return Optional.empty();
        }
        return marketRepository.findMarketPrice(market)
                .or(() -> composeMarketPrice(market))
                .map(marketPrice -> marketPrice.exchange(amount));
    }

    private Optional<MarketPrice> composeMarketPrice(Market market) {
        log.info("Composing market price for market {}", market);
        List<MarketPrice> baseMarketPrices = marketRepository.findMarketPrices(market.getBase());
        List<MarketPrice> quoteMarketPrices = marketRepository.findMarketPrices(market.getQuote());
        List<MarketPrice> concatedMarketPrices = Stream.concat(baseMarketPrices.stream(), quoteMarketPrices.stream()).toList();
        log.info("Found {} market prices for market {}", concatedMarketPrices.size(), market);
        return new MarketPrices(concatedMarketPrices).findMarketPrice(market);
    }

    private boolean isConversionPossible(Market market, BigDecimal amount) {
        return market != null && amount != null && BigDecimal.ZERO.compareTo(amount) < 0 &&
                marketManagementPort.isEnabled(market);
    }
}
