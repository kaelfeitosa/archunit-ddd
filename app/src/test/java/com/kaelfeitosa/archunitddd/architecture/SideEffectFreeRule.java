package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.lang.conditions.ArchConditions.haveRawReturnType;
import static com.tngtech.archunit.lang.conditions.ArchConditions.not;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

public class SideEffectFreeRule {
    public static class C21OperationMustHaveAReturnParameter {
        @ArchTest
        public static final ArchRule operationMustHaveAReturnParameter = methods()
                .that().areAnnotatedWith(SideEffectFree.class)
                .should(not(haveRawReturnType("void")));
    }
}
