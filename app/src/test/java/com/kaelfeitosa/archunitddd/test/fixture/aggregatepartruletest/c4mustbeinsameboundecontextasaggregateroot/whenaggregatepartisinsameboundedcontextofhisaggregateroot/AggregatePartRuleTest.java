package com.kaelfeitosa.archunitddd.test.fixture.aggregatepartruletest.c4mustbeinsameboundecontextasaggregateroot.whenaggregatepartisinsameboundedcontextofhisaggregateroot;

import com.kaelfeitosa.archunitddd.architecture.AggregatePart;
import com.kaelfeitosa.archunitddd.architecture.Entity;
import org.junit.jupiter.api.Nested;

public class AggregatePartRuleTest {
    public class C4MusBeInSameBoundedContextAsAggregateRoot {
        @Nested
        public class WhenAggregatePartIsInSameBoundedContextOfHisAggregateRoot {
            class AnAggregateRoot {}
            @Entity
            @AggregatePart(aggregateRoot = AnAggregateRoot.class)
            class AnAggregatePart { }
        }
    }
}
