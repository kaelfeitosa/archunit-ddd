package com.kaelfeitosa.archunitddd.domain.orders.order;

import com.kaelfeitosa.archunitddd.architecture.AggregatePart;
import com.kaelfeitosa.archunitddd.architecture.ValueObject;

@ValueObject
@AggregatePart(aggregateRoot = Order.class)
public class Price {
    private final double value;
    private final String currency;

    public Price(double value, String currency) {
        this.value = value;
        this.currency = currency;
    }

    public double getValue() {
        return value;
    }
}
