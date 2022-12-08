package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;

import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.anyOf;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;
import static java.lang.String.format;

public class ClosureRule {
    public static class C17MustNotBeSpecificationValidationOrIdentityOperation {
        @ArchTest
        public static final ArchRule mustNotBeSpecificationValidationOrIdentityOperation = methods()
                .that().areAnnotatedWith(Closure.class)
                .should().notBeAnnotatedWith(anyOf(
                        DefinesIdentity.class,
                        ValidatesSpec.class));
    }

    public static class C18ReturnParameterTypeMustConformInputParameterType {
        @ArchTest
        public static final ArchRule returnParameterTypeMustConformInputParameterType = methods()
                .that().areAnnotatedWith(Closure.class)
                .should(haveReturnParameterTypeMustConformInputParameterType());

        private static ArchCondition<? super JavaMethod> haveReturnParameterTypeMustConformInputParameterType() {
            return new ArchCondition<JavaMethod>("have return parameter type must conform input parameter type") {
                @Override
                public void check(JavaMethod javaMethod, ConditionEvents events) {
                    var parameterTypes = javaMethod.getRawParameterTypes();

                    if (parameterTypes.isEmpty())
                        events.add(violated(javaMethod, "There is no parameter."));

                    if (parameterTypes.size() > 1)
                        events.add(violated(javaMethod, format("There is more than one parameter. parameters: %s", parameterTypes)));

                    if (parameterTypes.size() >= 1) {
                        JavaType parameterType = parameterTypes.get(0);
                        JavaClass rawReturnType = javaMethod.getRawReturnType();

                        if (!parameterType.equals(rawReturnType)) {
                            events.add(violated(javaMethod,
                                    format("Input type and return type are different. input type: %s, return type: %s",
                                            parameterType.getName(),
                                            rawReturnType.getName())));
                        }
                    }
                }
            };
        }
    }
}
