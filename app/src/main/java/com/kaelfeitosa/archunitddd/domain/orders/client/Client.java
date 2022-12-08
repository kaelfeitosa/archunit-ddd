package com.kaelfeitosa.archunitddd.domain.orders.client;

import com.kaelfeitosa.archunitddd.architecture.AggregateRoot;
import com.kaelfeitosa.archunitddd.architecture.DefinesIdentity;
import com.kaelfeitosa.archunitddd.architecture.Entity;

import static java.util.Objects.nonNull;

@AggregateRoot
@Entity
public class Client {
    @DefinesIdentity
    private final String clientId;
    private final Fidelity fidelity;

    public Client(String clientId, Fidelity fidelity) {
        this.clientId = clientId;
        this.fidelity = fidelity;
    }

    public boolean hasFidelity() {
        return nonNull(fidelity) && fidelity.isValid();
    }
}
