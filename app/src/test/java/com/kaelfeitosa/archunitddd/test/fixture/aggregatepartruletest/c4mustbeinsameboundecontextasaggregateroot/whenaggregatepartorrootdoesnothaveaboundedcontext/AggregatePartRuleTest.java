package com.kaelfeitosa.archunitddd.test.fixture.aggregatepartruletest.c4mustbeinsameboundecontextasaggregateroot.whenaggregatepartorrootdoesnothaveaboundedcontext;

import com.kaelfeitosa.archunitddd.architecture.AggregatePart;
import com.kaelfeitosa.archunitddd.architecture.Entity;
import org.junit.jupiter.api.Nested;

public class AggregatePartRuleTest {
    public class C4MusBeInSameBoundedContextAsAggregateRoot {
        @Nested
        public class WhenAggregatePartOrRootDoestNotHaveABoundedContext {
            class AnAggregateRoot {}
            @Entity
            @AggregatePart(aggregateRoot = AnAggregateRoot.class)
            class AnAggregatePart { }
        }
    }
}
