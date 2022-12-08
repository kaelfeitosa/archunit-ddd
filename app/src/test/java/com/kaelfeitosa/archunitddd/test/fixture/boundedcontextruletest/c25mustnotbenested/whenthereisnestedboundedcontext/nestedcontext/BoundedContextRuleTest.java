package com.kaelfeitosa.archunitddd.test.fixture.boundedcontextruletest.c25mustnotbenested.whenthereisnestedboundedcontext.nestedcontext;

import com.kaelfeitosa.archunitddd.architecture.Entity;
import org.junit.jupiter.api.Nested;

public class BoundedContextRuleTest {
    public class C25MustNotBeNested {
        @Nested
        public class WhenThereIsNestedBoundedContext {
            @Entity
            class AnEntity {}
        }
    }
}
