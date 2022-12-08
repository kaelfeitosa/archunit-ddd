package com.kaelfeitosa.archunitddd.test;

import com.kaelfeitosa.archunitddd.architecture.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.kaelfeitosa.archunitddd.architecture.DefinesIdentityRule.C19MustNotBeSpecificationValidationOperation.mustNotBeSpecificationValidationOperation;
import static com.kaelfeitosa.archunitddd.architecture.DefinesIdentityRule.C20MayOnlyBeAppliedWithinEntities.mayOnlyBeAppliedWithinEntities;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.importClasses;
import static com.kaelfeitosa.archunitddd.test.helper.EvaluationResultAssert.assertThat;

class DefinesIdentityRuleTest {
    @Nested
    class C19MustNotBeSpecificationValidationOperation {
        @Nested
        class WhenMethodIsOnlyDefinesIdentity {
            class AClass {
                @DefinesIdentity
                int id() { return 0; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mustNotBeSpecificationValidationOperation.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenMethodDefinesIdentityAndIsAClosure {
            class AClass {
                @DefinesIdentity
                @Closure
                int id() { return 0; }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mustNotBeSpecificationValidationOperation.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenMethodDefinesIdentityAndIsAValidation {
            class AClass {
                @DefinesIdentity
                @ValidatesSpec
                int id() { return 0; }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mustNotBeSpecificationValidationOperation.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C20MayOnlyBeAppliedWithinEntities {
        @Nested
        class WhenMethodDeclaredWithinEntity {
            @Entity
            class AClass {
                @DefinesIdentity
                int id() {
                    return 0;
                }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mayOnlyBeAppliedWithinEntities.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenFieldDeclaredWithinEntity {
            @Entity
            class AClass {
                @DefinesIdentity
                int id;
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mayOnlyBeAppliedWithinEntities.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenMethodDeclaredWithoutEntity {
            class AClass {
                @DefinesIdentity
                int id() {
                    return 0;
                }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mayOnlyBeAppliedWithinEntities.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenFieldDeclaredWithoutEntity {
            class AClass {
                @DefinesIdentity
                int id;
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mayOnlyBeAppliedWithinEntities.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }
}