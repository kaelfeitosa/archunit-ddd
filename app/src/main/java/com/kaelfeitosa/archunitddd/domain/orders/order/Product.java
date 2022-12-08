package com.kaelfeitosa.archunitddd.domain.orders.order;

import com.kaelfeitosa.archunitddd.architecture.AggregatePart;
import com.kaelfeitosa.archunitddd.architecture.DefinesIdentity;
import com.kaelfeitosa.archunitddd.architecture.Entity;

@AggregatePart(aggregateRoot = Order.class)
@Entity
public class Product {
    @DefinesIdentity
    private final String productId;
    private final Price price;

    public Product(String productId, Price price) {
        this.productId = productId;
        this.price = price;
    }

    public Price getPrice() {
        return price;
    }
}
