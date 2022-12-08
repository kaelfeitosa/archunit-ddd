package com.kaelfeitosa.archunitddd.test;

import com.kaelfeitosa.archunitddd.architecture.Entity;
import com.kaelfeitosa.archunitddd.architecture.Service;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.kaelfeitosa.archunitddd.architecture.ServiceRule.C11ClassHasNoOtherStereotypes.classHasNoOtherStereotypes;
import static com.kaelfeitosa.archunitddd.architecture.ServiceRule.C12ClassContainsOnlyOperationsAndAtLeastOne.classContainsOnlyOperationsAndAtLeastOne;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.importClasses;
import static com.kaelfeitosa.archunitddd.test.helper.EvaluationResultAssert.assertThat;

class ServiceRuleTest {
    @Nested
    class C11ClassHasNoOtherStereotypes {
        @Nested
        class WhenHasOnlyServiceStereotype {
            @Service
            class AnService {
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = classHasNoOtherStereotypes.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }


        @Nested
        class WhenHasAnotherStereotype {
            @Service
            @Entity
            class AnService { }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = classHasNoOtherStereotypes.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C12ClassContainsOnlyOperationsAndAtLeastOne {
        @Nested
        class WhenHasOnlyOperations {
            @Service
            class SenderService {
                void batchSenderOrders() { }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = classContainsOnlyOperationsAndAtLeastOne.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenThereIsNoOperations {
            @Service
            class ReceiverService {
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = classContainsOnlyOperationsAndAtLeastOne.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenHasProperties {
            @Entity
            class AnEntity {}

            @Service
            class AnService {
                AnEntity anEntity;
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = classContainsOnlyOperationsAndAtLeastOne.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }
}