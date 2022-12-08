package com.kaelfeitosa.archunitddd.test.fixture.boundedcontextruletest.c24mustnothavemodulestereotype.whenthereisnomodulestereotype;

import com.kaelfeitosa.archunitddd.architecture.Entity;
import org.junit.jupiter.api.Nested;

public class BoundedContextRuleTest {
    public class C24MustNotHaveModuleStereotype {
        @Nested
        public class WhenThereIsNoModuleStereotype {
            @Entity
            class AnEntity {}
        }
    }
}
