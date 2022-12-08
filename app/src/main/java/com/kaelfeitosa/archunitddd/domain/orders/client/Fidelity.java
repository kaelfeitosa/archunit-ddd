package com.kaelfeitosa.archunitddd.domain.orders.client;

import com.kaelfeitosa.archunitddd.architecture.AggregatePart;
import com.kaelfeitosa.archunitddd.architecture.ValueObject;

import java.util.Date;

@AggregatePart(aggregateRoot = Client.class)
@ValueObject
public class Fidelity {
    private final String type;
    private final Date date;

    public Fidelity(String type, Date date) {
        this.type = type;
        this.date = date;
    }

    public boolean isValid() {
        return date.after(new Date());
    }
}
