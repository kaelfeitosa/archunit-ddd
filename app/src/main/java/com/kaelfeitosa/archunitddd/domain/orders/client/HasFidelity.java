package com.kaelfeitosa.archunitddd.domain.orders.client;

import com.kaelfeitosa.archunitddd.architecture.Spec;
import com.kaelfeitosa.archunitddd.architecture.ValidatesSpec;

@Spec(specified = Client.class)
public class HasFidelity {
    @ValidatesSpec
    public boolean satisfies(Client client) {
        return client.hasFidelity();
    }
}
