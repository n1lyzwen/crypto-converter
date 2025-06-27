package com.crypto.conversion.application;

import com.crypto.conversion.domain.ConvertionPrice;
import com.crypto.conversion.domain.Market;

import java.math.BigDecimal;

public interface MarketPriceConvertionPort {

    ConvertionPrice convert(Market market, BigDecimal amount);

}
