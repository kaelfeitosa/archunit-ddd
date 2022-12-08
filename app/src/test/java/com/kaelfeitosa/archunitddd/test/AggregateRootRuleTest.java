package com.kaelfeitosa.archunitddd.test;

import com.kaelfeitosa.archunitddd.architecture.AggregatePart;
import com.kaelfeitosa.archunitddd.architecture.AggregateRoot;
import com.kaelfeitosa.archunitddd.architecture.Entity;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.kaelfeitosa.archunitddd.architecture.AggregateRootRule.C5OnlyEntitiesMayBeAggregateRoots.onlyEntitiesMayBeAggregateRoots;
import static com.kaelfeitosa.archunitddd.architecture.AggregateRootRule.C6AggregateMustContainAtLeastOnePart.aggregateMustContainAtLeastOnePart;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.importClasses;
import static com.kaelfeitosa.archunitddd.test.helper.EvaluationResultAssert.assertThat;

class AggregateRootRuleTest {
    @Nested
    class C5OnlyEntitiesMayBeAggregateRoots {
        @Nested
        class WhenAggregateRootIsAnEntity {
            @Entity
            @AggregateRoot
            class AnAggregateRoot { }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = onlyEntitiesMayBeAggregateRoots.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenAggregatePartIsNotAnEntity {
            @AggregateRoot
            class AnAggregateRoot { }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = onlyEntitiesMayBeAggregateRoots.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C6AggregateMustContainAtLeastOnePart {
        @Nested
        class WhenAggregatePartIsAnEntity {
            @Entity
            @AggregateRoot
            class AnAggregateRoot {
                AnAggregatePart anAggregatePart;
            }

            @Entity
            @AggregatePart(aggregateRoot = AnAggregateRoot.class)
            class AnAggregatePart {}

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = aggregateMustContainAtLeastOnePart.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenAggregatePartIsNotAnEntity {
            @AggregateRoot
            class AnAggregateRoot { }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = aggregateMustContainAtLeastOnePart.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }
}