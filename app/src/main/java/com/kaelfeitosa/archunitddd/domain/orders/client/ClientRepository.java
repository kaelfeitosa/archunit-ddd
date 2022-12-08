package com.kaelfeitosa.archunitddd.domain.orders.client;

import com.kaelfeitosa.archunitddd.architecture.Repository;

@Repository
public interface ClientRepository {
    Client findClientById(String clientId);
}