package com.kaelfeitosa.archunitddd.test;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.kaelfeitosa.archunitddd.architecture.BoundedContextRule.C24MustNotHaveModuleStereotype.mustNotHaveModuleStereotype;
import static com.kaelfeitosa.archunitddd.architecture.BoundedContextRule.C25MustNotBeNested.mustNotBeNested;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.importClasses;
import static com.kaelfeitosa.archunitddd.test.helper.EvaluationResultAssert.assertThat;

class BoundedContextRuleTest {
    @Nested
    class C24MustNotHaveModuleStereotype {
        @Nested
        class WhenThereIsNoModuleStereotype {
            @Test
            void isValid() {
                var javaClasses = importClasses("com.kaelfeitosa.archunitddd.test.fixture.boundedcontextruletest.c24mustnothavemodulestereotype.whenthereisnomodulestereotype");
                var evaluationResult = mustNotHaveModuleStereotype.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenThereIsModuleStereotype {
            @Test
            void isInvalid() {
                var javaClasses = importClasses("com.kaelfeitosa.archunitddd.test.fixture.boundedcontextruletest.c24mustnothavemodulestereotype.whenthereismodulestereotype");
                var evaluationResult = mustNotHaveModuleStereotype.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C25MustNotBeNested {
        @Nested
        class WhenThereIsNoNestedBoundedContext {
            @Test
            void isValid() {
                var javaClasses = importClasses("com.kaelfeitosa.archunitddd.test.fixture.boundedcontextruletest.c25mustnotbenested.whenthereisnonestedboundedcontext");
                var evaluationResult = mustNotBeNested.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenThereIsNestedBoundedContext {
            @Test
            void isInvalid() {
                var javaClasses = importClasses("com.kaelfeitosa.archunitddd.test.fixture.boundedcontextruletest.c25mustnotbenested.whenthereisnestedboundedcontext");
                var evaluationResult = mustNotBeNested.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }
}