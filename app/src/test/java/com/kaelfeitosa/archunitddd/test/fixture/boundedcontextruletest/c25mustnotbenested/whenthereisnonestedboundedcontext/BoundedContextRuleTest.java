package com.kaelfeitosa.archunitddd.test.fixture.boundedcontextruletest.c25mustnotbenested.whenthereisnonestedboundedcontext;

import com.kaelfeitosa.archunitddd.architecture.Entity;
import org.junit.jupiter.api.Nested;

public class BoundedContextRuleTest {
    public class C25MustNotBeNested {
        @Nested
        public class WhenThereIsNoNestedBoundedContext {
            @Entity
            class AnEntity {}
        }
    }
}
