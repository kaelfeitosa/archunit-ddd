package com.kaelfeitosa.archunitddd.domain.orders.order;

import com.kaelfeitosa.archunitddd.architecture.Repository;

@Repository
public interface OrderRepository {
    void save(Order order);
}
