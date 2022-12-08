package com.kaelfeitosa.archunitddd.test;

import com.kaelfeitosa.archunitddd.architecture.Spec;
import com.kaelfeitosa.archunitddd.architecture.ValidatesSpec;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.kaelfeitosa.archunitddd.architecture.ValidatesSpecRule.C22MustHaveBooleanTypedReturnParameter.mustHaveBooleanTypedReturnParameter;
import static com.kaelfeitosa.archunitddd.architecture.ValidatesSpecRule.C23MayOnlyBeAppliedWithinSpecifications.mayOnlyBeAppliedWithinSpecifications;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.importClasses;
import static com.kaelfeitosa.archunitddd.test.helper.EvaluationResultAssert.assertThat;

class ValidatesSpecRuleTest {
    @Nested
    class C22MustHaveBooleanTypedReturnParameter {
        @Nested
        class WhenMethodHasReturnTypeBooleanClass {
            class AClass {
                @ValidatesSpec
                Boolean aValidatesSpec() { return Boolean.FALSE; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mustHaveBooleanTypedReturnParameter.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenMethodHasReturnTypeBooleanPrimitive {
            class AClass {
                @ValidatesSpec
                boolean aValidatesSpec() { return false; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mustHaveBooleanTypedReturnParameter.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenMethodHasAnotherReturnType {
            class AClass {
                @ValidatesSpec
                int aValidatesSpec() { return 0; }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mustHaveBooleanTypedReturnParameter.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }

        @Nested
        class WhenMethodHasNoReturnType {
            class AClass {
                @ValidatesSpec
                void aValidatesSpec() {}
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mustHaveBooleanTypedReturnParameter.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }

    @Nested
    class C23MayOnlyBeAppliedWithinSpecifications {
        @Nested
        class WhenMethodDeclaredWithinEntity {
            @Spec(specified = Object.class)
            class AClass {
                @ValidatesSpec
                boolean aValidatesSpec() { return false; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mayOnlyBeAppliedWithinSpecifications.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenMethodDeclaredWithoutEntity {
            class AClass {
                @ValidatesSpec
                boolean aValidatesSpec() { return false; }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = mayOnlyBeAppliedWithinSpecifications.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }
}