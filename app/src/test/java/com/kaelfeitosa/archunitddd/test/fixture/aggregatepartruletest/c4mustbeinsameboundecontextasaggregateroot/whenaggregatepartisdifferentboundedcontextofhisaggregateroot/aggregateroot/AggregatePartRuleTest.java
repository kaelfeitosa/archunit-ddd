package com.kaelfeitosa.archunitddd.test.fixture.aggregatepartruletest.c4mustbeinsameboundecontextasaggregateroot.whenaggregatepartisdifferentboundedcontextofhisaggregateroot.aggregateroot;

import org.junit.jupiter.api.Nested;

public class AggregatePartRuleTest {
    public class C4MusBeInSameBoundedContextAsAggregateRoot {
        @Nested
        public class WhenAggregatePartIsInDifferentBoundedContextOfHisAggregateRoot {
            public class AnAggregateRoot {}
        }
    }
}
