package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.members;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

public class ValidatesSpecRule {
    public static class C22MustHaveBooleanTypedReturnParameter {
        @ArchTest
        public static final ArchRule mustHaveBooleanTypedReturnParameter = methods()
                .that().areAnnotatedWith(ValidatesSpec.class)
                .should().haveRawReturnType(Boolean.class)
                .orShould().haveRawReturnType(boolean.class);
    }

    public static class C23MayOnlyBeAppliedWithinSpecifications {
        @ArchTest
        public static final ArchRule mayOnlyBeAppliedWithinSpecifications = members()
                .that().areAnnotatedWith(ValidatesSpec.class)
                .should().beDeclaredInClassesThat(are(annotatedWith(Spec.class)));
    }
}
