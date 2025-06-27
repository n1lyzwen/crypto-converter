package com.crypto.conversion.infrastructure.persistence.mongo;

import org.springframework.data.mongodb.repository.MongoRepository;

interface MongoMarketAvailabilityRepository extends MongoRepository<MarketAvailabilityDocument, MarketId> {
}
