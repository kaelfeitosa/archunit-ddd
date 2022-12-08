package com.kaelfeitosa.archunitddd.test;

import com.kaelfeitosa.archunitddd.architecture.*;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.kaelfeitosa.archunitddd.architecture.SideEffectFreeRule.C21OperationMustHaveAReturnParameter.operationMustHaveAReturnParameter;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.importClasses;
import static com.kaelfeitosa.archunitddd.test.helper.EvaluationResultAssert.assertThat;

class SideEffectFreeRuleTest {
    @Nested
    class C21OperationMustHaveAReturnParameter {
        @Nested
        class WhenMethodHasReturnType {
            class AClass {
                @SideEffectFree
                int aSideEffectFree() { return 0; }
            }

            @Test
            void isValid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = operationMustHaveAReturnParameter.evaluate(javaClasses);
                assertThat(evaluationResult).isValid();
            }
        }

        @Nested
        class WhenMethodDefinesIdentityAndIsAClosure {
            class AClass {
                @SideEffectFree
                void aSideEffectFree() {  }
            }

            @Test
            void isInvalid() {
                var javaClasses = importClasses(super.getClass());
                var evaluationResult = operationMustHaveAReturnParameter.evaluate(javaClasses);
                assertThat(evaluationResult).isInvalid();
            }
        }
    }
}