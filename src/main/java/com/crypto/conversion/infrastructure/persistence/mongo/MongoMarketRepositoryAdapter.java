package com.crypto.conversion.infrastructure.persistence.mongo;

import com.crypto.conversion.application.MarketRepository;
import com.crypto.conversion.domain.Market;
import com.crypto.conversion.domain.MarketPrice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
class MongoMarketRepositoryAdapter implements MarketRepository {

    private final MongoMarketPriceRepository marketPriceRepository;
    private final MongoMarketAvailabilityRepository marketAvailabilityRepository;

    @Override
    public void removeMarketPrice(Market market) {
        marketPriceRepository.deleteById(new MarketId(market));
    }

    @Override
    public void updateMarketPrice(MarketPrice marketPrice) {
        marketPriceRepository.save(MarketPriceDocument.from(marketPrice));
    }

    @Override
    public Optional<MarketPrice> findMarketPrice(Market market) {
        return marketPriceRepository.findById(new MarketId(market))
                .map(MarketPriceDocument::toMarketPrice);
    }

    @Override
    public List<MarketPrice> findMarketPrices(String token) {
        return marketPriceRepository.findByBaseOrQuote(token, token)
                .stream()
                .map(MarketPriceDocument::toMarketPrice)
                .toList();
    }

    @Override
    public void switchOff(Market market) {
        MarketId id = new MarketId(market);
        marketAvailabilityRepository.save(new MarketAvailabilityDocument(id, false));
    }

    @Override
    public void switchOn(Market market) {
        MarketId id = new MarketId(market);
        marketAvailabilityRepository.save(new MarketAvailabilityDocument(id, true));
    }

    @Override
    public Optional<Boolean> isMarketEnabled(Market market) {
        return marketAvailabilityRepository.findById(new MarketId(market))
                .map(MarketAvailabilityDocument::isEnabled);
    }
}
