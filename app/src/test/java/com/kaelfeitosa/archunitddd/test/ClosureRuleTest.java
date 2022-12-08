package com.kaelfeitosa.archunitddd.test;

import com.kaelfeitosa.archunitddd.architecture.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.kaelfeitosa.archunitddd.architecture.ClosureRule.C17MustNotBeSpecificationValidationOrIdentityOperation.mustNotBeSpecificationValidationOrIdentityOperation;
import static com.kaelfeitosa.archunitddd.architecture.ClosureRule.C18ReturnParameterTypeMustConformInputParameterType.returnParameterTypeMustConformInputParameterType;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.importClasses;
import static com.kaelfeitosa.archunitddd.test.helper.EvaluationResultAssert.assertThat;

class ClosureRuleTest {
    @Nested
    class C17MustNotBeSpecificationValidationOrIdentityOperation {
        @Nested
        class WhenMethodIsOnlyAClosure {
            class AClass {
                @Closure
                String aClosure(String string) { return null; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mustNotBeSpecificationValidationOrIdentityOperation.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenMethodIsAClosureAndValidation {
            class AClass {
                @Closure
                @ValidatesSpec
                String aClosure(String string) { return null; }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mustNotBeSpecificationValidationOrIdentityOperation.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenMethodIsAClosureAndIdentity {
            class AClass {
                @Closure
                @DefinesIdentity
                String aClosure(String string) { return null; }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mustNotBeSpecificationValidationOrIdentityOperation.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C18ReturnParameterTypeMustConformInputParameterType {
        @Nested
        class WhenParameterAndReturnTypeAreEquals {
            class AClass {
                @Closure
                String aClosure(String string) {
                    return null;
                }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = returnParameterTypeMustConformInputParameterType.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenParameterAndReturnTypeAreDifferent {
            class AClass {
                @Closure
                String aClosure(Integer integer) {
                    return null;
                }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = returnParameterTypeMustConformInputParameterType.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenHasMoreThanOneParameter {
            class AClass {
                @Closure
                String aClosure(String string, String another) {
                    return null;
                }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = returnParameterTypeMustConformInputParameterType.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenHasNoParameter {
            class AClass {
                @Closure
                String aClosure(String string, String another) {
                    return null;
                }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = returnParameterTypeMustConformInputParameterType.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }
}