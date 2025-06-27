package com.crypto.conversion.application;

import com.crypto.conversion.domain.Market;

public interface MarketManagementPort {

    void disable(Market market);

    void enable(Market market);

}
