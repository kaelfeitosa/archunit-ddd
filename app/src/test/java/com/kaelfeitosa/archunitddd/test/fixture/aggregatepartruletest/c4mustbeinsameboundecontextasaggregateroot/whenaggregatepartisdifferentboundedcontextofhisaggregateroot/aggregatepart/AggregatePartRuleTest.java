package com.kaelfeitosa.archunitddd.test.fixture.aggregatepartruletest.c4mustbeinsameboundecontextasaggregateroot.whenaggregatepartisdifferentboundedcontextofhisaggregateroot.aggregatepart;

import com.kaelfeitosa.archunitddd.architecture.AggregatePart;
import com.kaelfeitosa.archunitddd.architecture.Entity;
import org.junit.jupiter.api.Nested;

public class AggregatePartRuleTest {
    public class C4MusBeInSameBoundedContextAsAggregateRoot {
        @Nested
        public class WhenAggregatePartIsInDifferentBoundedContextOfHisAggregateRoot {
            @Entity
            @AggregatePart(aggregateRoot = com.kaelfeitosa.archunitddd.test.fixture.aggregatepartruletest.c4mustbeinsameboundecontextasaggregateroot.whenaggregatepartisdifferentboundedcontextofhisaggregateroot.aggregateroot.AggregatePartRuleTest.C4MusBeInSameBoundedContextAsAggregateRoot.WhenAggregatePartIsInDifferentBoundedContextOfHisAggregateRoot.AnAggregateRoot.class)
            class AnAggregatePart { }
        }
    }
}
