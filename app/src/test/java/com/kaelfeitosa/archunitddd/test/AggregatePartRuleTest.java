package com.kaelfeitosa.archunitddd.test;

import com.kaelfeitosa.archunitddd.architecture.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.kaelfeitosa.archunitddd.architecture.AggregatePartRule.C1OnlyEntitiesAndValueObjectsMayBeAggregateParts.onlyEntitiesAndValueObjectsMayBeAggregateParts;
import static com.kaelfeitosa.archunitddd.architecture.AggregatePartRule.C2AssignedAggregateRootMustHaveAggregateRootStereotype.entitiesAndValueObjectsAreAggregateParts;
import static com.kaelfeitosa.archunitddd.architecture.AggregatePartRule.C3NoIncomingAssociationsFromOutsideTheAggregate.noIncomingAssociationsFromOutsideTheAggregate;
import static com.kaelfeitosa.archunitddd.architecture.AggregatePartRule.C4MusBeInSameBoundedContextAsAggregateRoot.mustBeInSameBoundedContextAsAggregateRoot;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.importClasses;
import static org.assertj.core.api.Assertions.assertThat;
import static com.kaelfeitosa.archunitddd.test.helper.EvaluationResultAssert.assertThat;

class AggregatePartRuleTest {
    @Nested
    class C1OnlyEntitiesAndValueObjectsMayBeAggregateParts {
        @Nested
        class WhenAggregatePartIsAnEntity {
            @Entity
            @AggregatePart(aggregateRoot = Object.class)
            class AnEntityAggregatePart { }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = onlyEntitiesAndValueObjectsMayBeAggregateParts.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenAggregatePartIsAnotherClass {
            @ValueObject
            @AggregatePart(aggregateRoot = Object.class)
            class AValueObjectAggregatePart { }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = onlyEntitiesAndValueObjectsMayBeAggregateParts.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenAggregatePartIsNotAnEntityOrValueObject {
            @AggregatePart(aggregateRoot = Object.class)
            class AnotherComponent { }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = onlyEntitiesAndValueObjectsMayBeAggregateParts.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenComponentIsNotAnAggregatePart {
            @Entity
            class AnotherComponent { }

            @Test
            void hasNoViolations() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = onlyEntitiesAndValueObjectsMayBeAggregateParts.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }
    }

    @Nested
    class C2AssignedAggregateRootMustHaveAggregateRootStereotype {
        @Nested
        class WhenAggregatePartAssignedAggregateRootHasAggregateRootStereotype {
            @AggregateRoot
            class AnAggregateRoot {}

            @Entity
            @AggregatePart(aggregateRoot = AnAggregateRoot.class)
            class AnAggregatePart { }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = entitiesAndValueObjectsAreAggregateParts.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenAggregatePartAssignedAggregateRootHasNotAggregateRootStereotype {
            class AnAggregateRoot {}

            @Entity
            @AggregatePart(aggregateRoot = AnAggregateRoot.class)
            class AnAggregatePart { }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = entitiesAndValueObjectsAreAggregateParts.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C3NoIncomingAssociationsFromOutsideTheAggregate {
        @Nested
        class WhenAssociationsAreFromInsideTheAggregateRoot {
            @AggregateRoot
            class AnAggregateRoot {
                AnAggregatePart anAggregatePart;
            }

            @Entity
            @AggregatePart(aggregateRoot = AnAggregateRoot.class)
            class AnAggregatePart {
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = noIncomingAssociationsFromOutsideTheAggregate.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenAssociationsAreFromAggregatePartInsideTheAggregate {
            @AggregateRoot
            class AnAggregateRoot {
            }

            @Entity
            @AggregatePart(aggregateRoot = AnAggregateRoot.class)
            class AnAggregatePart {
            }

            @Entity
            @AggregatePart(aggregateRoot = AnAggregateRoot.class)
            class AnotherAggregatePart {
                AnAggregatePart anAggregatePart;
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = noIncomingAssociationsFromOutsideTheAggregate.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenAssociationsAreFromAnotherAggregateRoot {
            @AggregateRoot
            class AnAggregateRoot {

            }

            @Entity
            @AggregatePart(aggregateRoot = AnAggregateRoot.class)
            class AnAggregatePart {
            }

            @AggregateRoot
            class AnotherAggregateRoot {
                AnAggregatePart anAggregatePartFromAnotherAggregateRoot;
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = noIncomingAssociationsFromOutsideTheAggregate.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenAssociationsAreFromAggregatePartFromAnotherAggregate {
            @AggregateRoot
            class AnAggregateRoot {

            }

            @Entity
            @AggregatePart(aggregateRoot = AnAggregateRoot.class)
            class AnAggregatePart {
            }

            @AggregateRoot
            class AnotherAggregateRoot {
            }

            @Entity
            @AggregatePart(aggregateRoot = AnotherAggregateRoot.class)
            class AnotherAggregatePart {
                AnAggregatePart anAggregatePart;
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = noIncomingAssociationsFromOutsideTheAggregate.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C4MusBeInSameBoundedContextAsAggregateRoot {
        @Nested
        class WhenAggregatePartIsInSameBoundedContextOfHisAggregateRoot {
            @Test
            void isValid() {
                var javaClasses = importClasses("com.kaelfeitosa.archunitddd.test.fixture.aggregatepartruletest.c4mustbeinsameboundecontextasaggregateroot.whenaggregatepartisinsameboundedcontextofhisaggregateroot");
                var evaluationResult = mustBeInSameBoundedContextAsAggregateRoot.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenAggregatePartIsInDifferentBoundedContextOfHisAggregateRoot {
            @Test
            void isInvalid() {
                var javaClasses = importClasses("com.kaelfeitosa.archunitddd.test.fixture.aggregatepartruletest.c4mustbeinsameboundecontextasaggregateroot.whenaggregatepartisdifferentboundedcontextofhisaggregateroot");
                var evaluationResult = mustBeInSameBoundedContextAsAggregateRoot.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenAggregatePartOrRootDoestNotHaveABoundedContext {
            @Test
            void isInvalid() {
                var javaClasses = importClasses("com.kaelfeitosa.archunitddd.test.fixture.aggregatepartruletest.c4mustbeinsameboundecontextasaggregateroot.whenaggregatepartorrootdoesnothaveaboundedcontext");
                var evaluationResult = mustBeInSameBoundedContextAsAggregateRoot.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
                assertThat(evaluationResult.getFailureReport().getDetails()).hasSize(2);
            }
        }
    }
}