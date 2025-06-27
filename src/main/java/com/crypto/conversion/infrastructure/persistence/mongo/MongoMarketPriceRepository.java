package com.crypto.conversion.infrastructure.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

interface MongoMarketPriceRepository extends MongoRepository<MarketPriceDocument, MarketId> {

    List<MarketPriceDocument> findByBaseOrQuote(String base, String quote);

}
