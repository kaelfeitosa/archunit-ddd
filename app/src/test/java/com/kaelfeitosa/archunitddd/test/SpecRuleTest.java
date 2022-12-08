package com.kaelfeitosa.archunitddd.test;

import com.kaelfeitosa.archunitddd.architecture.DefinesIdentity;
import com.kaelfeitosa.archunitddd.architecture.Entity;
import com.kaelfeitosa.archunitddd.architecture.Spec;
import com.kaelfeitosa.archunitddd.architecture.ValidatesSpec;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.kaelfeitosa.archunitddd.architecture.SpecRule.C13ClassHasNoOtherStereotypes.classHasNoOtherStereotypes;
import static com.kaelfeitosa.archunitddd.architecture.SpecRule.C14ClassContainsAtLeastOneValidationOperation.classContainsAtLeastOneValidationOperation;
import static com.kaelfeitosa.archunitddd.architecture.SpecRule.C15AtLeastOneDomainObjectIsSpecified.atLeastOneDomainObjectIsSpecified;
import static com.kaelfeitosa.archunitddd.architecture.SpecRule.C16ValidationOperationHasParameterTypedAsSpecifiedObject.validationOperationHasParameterTypedAsSpecifiedObject;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.importClasses;
import static com.kaelfeitosa.archunitddd.test.helper.EvaluationResultAssert.assertThat;

class SpecRuleTest {
    @Nested
    class C13ClassHasNoOtherStereotypes {
        @Nested
        class WhenHasOnlySpecStereotype {
            @Spec(specified = Class.class)
            class AnSpec {
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
            @Spec(specified = Class.class)
            @Entity
            class AnSpec { }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = classHasNoOtherStereotypes.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C14ClassContainsAtLeastOneValidationOperation {
        @Nested
        class WhenHasOnlyValidationOperation {
            @Entity
            class AnEntity {}

            @Spec(specified = Class.class)
            class AnSpec {
                @ValidatesSpec
                boolean Is(AnEntity anEntity) { return true; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = classContainsAtLeastOneValidationOperation.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenThereIsNoOperations {
            @Spec(specified = Class.class)
            class AnSpec {
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = classContainsAtLeastOneValidationOperation.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenThereIsOtherOperations {
            @Spec(specified = Class.class)
            class AnSpec {
                @DefinesIdentity
                int id() { return 0; }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = classContainsAtLeastOneValidationOperation.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C15AtLeastOneDomainObjectIsSpecified {
        @Nested
        class WhenADomainObjectIsSpecified {
            @Entity
            class AnEntity {}

            @Spec(specified = AnEntity.class)
            class AnSpec {
                @ValidatesSpec
                boolean Is(AnEntity anEntity) { return true; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = atLeastOneDomainObjectIsSpecified.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenADomainObjectIsNotSpecified {
            @Spec(specified = Class.class)
            class AnSpec {
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = atLeastOneDomainObjectIsSpecified.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C16ValidationOperationHasParameterTypedAsSpecifiedObject {
        @Nested
        class WhenHasParameterTypesAsSpecifiedObject {
            @Entity
            class AnEntity {}

            @Spec(specified = AnEntity.class)
            class AnSpec {
                @ValidatesSpec
                boolean Is(AnEntity anEntity) { return true; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = validationOperationHasParameterTypedAsSpecifiedObject.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenHasParameterTypesAsDifferentObject {
            @Entity
            class AnEntity {}

            @Spec(specified = AnEntity.class)
            class AnSpec {
                @ValidatesSpec
                boolean Is(Object anEntity) { return true; }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = validationOperationHasParameterTypedAsSpecifiedObject.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenHasNoParameter {
            @Entity
            class AnEntity {}

            @Spec(specified = AnEntity.class)
            class AnSpec {
                @ValidatesSpec
                boolean Is() { return true; }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = validationOperationHasParameterTypedAsSpecifiedObject.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }
}