package com.crypto.conversion.application;

import com.crypto.conversion.domain.ConversionPrice;
import com.crypto.conversion.domain.Market;

import java.math.BigDecimal;
import java.util.Optional;

public interface MarketPriceConversionPort {

    Optional<ConversionPrice> convert(Market market, BigDecimal amount);

}
