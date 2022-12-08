package com.kaelfeitosa.archunitddd.test;

import com.kaelfeitosa.archunitddd.architecture.DefinesIdentity;
import com.kaelfeitosa.archunitddd.architecture.Entity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.kaelfeitosa.archunitddd.architecture.EntityRule.C7OneOperationOrAtLeastOnePropertyDefinesTheIdentity.oneOperationOrAtLeastOnePropertyDefinesTheIdentity;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.importClasses;
import static com.kaelfeitosa.archunitddd.test.helper.EvaluationResultAssert.assertThat;

class EntityRuleTest {
    @Nested
    class C7OneOperationOrAtLeastOnePropertyDefinesTheIdentity {
        @Nested
        class WhenHasAFieldThatDefinesTheIdentity {
            @Entity
            class AnEntity {
                @DefinesIdentity
                int id;
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = oneOperationOrAtLeastOnePropertyDefinesTheIdentity.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenHasAMethodThatDefinesTheIdentity {
            @Entity
            class AnEntity {
                @DefinesIdentity
                int id() { return 0; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = oneOperationOrAtLeastOnePropertyDefinesTheIdentity.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenThereIsNoMethodThatDefinesTheIdentity {
            @Entity
            class AnEntity { }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = oneOperationOrAtLeastOnePropertyDefinesTheIdentity.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }
}