package com.kaelfeitosa.archunitddd.test;

import com.kaelfeitosa.archunitddd.architecture.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.kaelfeitosa.archunitddd.architecture.RepositoryRule.C10OutgoingAssociationsMustPointToEntitiesOrValueObjects.outgoingAssociationsMustPointToEntitiesOrValueObjects;
import static com.kaelfeitosa.archunitddd.architecture.RepositoryRule.C8ClassHasNoOtherStereotypes.*;
import static com.kaelfeitosa.archunitddd.architecture.RepositoryRule.C9ClassContainsOnlyOperationsAndAtLeastOne.classContainsOnlyOperationsAndAtLeastOne;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.importClasses;
import static com.kaelfeitosa.archunitddd.test.helper.EvaluationResultAssert.assertThat;

class RepositoryRuleTest {
    @Nested
    class C8ClassHasNoOtherStereotypes {
        @Nested
        class WhenHasOnlyRepositoryStereotype {
            @Repository
            class AnRepository {
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
            @Repository
            @Entity
            class AnRepository { }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = classHasNoOtherStereotypes.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C9ClassContainsOnlyOperationsAndAtLeastOne {
        @Nested
        class WhenHasOnlyOperations {
            @Entity
            class AnEntity {}

            @Repository
            class AnRepository {
                AnEntity findEntity(int id) { return null; }
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
            @Repository
            class AnRepository {
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

            @Repository
            class AnRepository {
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

    @Nested
    class C10OutgoingAssociationsMustPointToEntitiesOrValueObjects {
        @Nested
        class WhenHasOutgoingAssociationToEntity {
            @Entity
            class AnEntity {}

            @Repository
            class AnRepository {
                AnEntity findEntity(int id) { return null; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = outgoingAssociationsMustPointToEntitiesOrValueObjects.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenHasOutgoingAssociationToValueObject {
            @ValueObject
            class AnValueObject {}

            @Repository
            class AnRepository {
                AnValueObject findNextId() { return null; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = outgoingAssociationsMustPointToEntitiesOrValueObjects.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenHasOutgoingAssociationToOther {
            class AnotherClass {}

            @Repository
            class AnRepository {
                AnotherClass findNextId() { return null; }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = outgoingAssociationsMustPointToEntitiesOrValueObjects.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }
}