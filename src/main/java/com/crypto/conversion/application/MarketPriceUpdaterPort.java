package com.crypto.conversion.application;

import com.crypto.conversion.domain.MarketPrice;

public interface MarketPriceUpdaterPort {

    void update(MarketPrice marketPrice);

}
