package com.kaelfeitosa.archunitddd.domain.orders.order;

import com.kaelfeitosa.archunitddd.architecture.AggregateRoot;
import com.kaelfeitosa.archunitddd.architecture.DefinesIdentity;
import com.kaelfeitosa.archunitddd.architecture.Entity;

@AggregateRoot
@Entity
public class Order {
    @DefinesIdentity
    private final String orderId;
    private final String clientId;
    private final Product product;
    private final int quantity;

    public Order(String orderId, String clientId, Product product, int quantity) {
        this.orderId = orderId;
        this.clientId = clientId;
        this.product = product;
        this.quantity = quantity;
    }

    public double getTotal() {
        return (double) quantity * product.getPrice().getValue();
    }
}
