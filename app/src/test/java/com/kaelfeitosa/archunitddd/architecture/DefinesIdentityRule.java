package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.anyOf;
import static com.tngtech.archunit.core.domain.properties.CanBeAnnotated.Predicates.annotatedWith;
import static com.tngtech.archunit.lang.conditions.ArchPredicates.are;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.members;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.methods;

public class DefinesIdentityRule {
    public static class C19MustNotBeSpecificationValidationOperation {
        @ArchTest
        public static final ArchRule mustNotBeSpecificationValidationOperation = methods()
                .that().areAnnotatedWith(DefinesIdentity.class)
                .should().notBeAnnotatedWith(anyOf(
                        Closure.class,
                        ValidatesSpec.class));
    }

    public static class C20MayOnlyBeAppliedWithinEntities {
        @ArchTest
        public static final ArchRule mayOnlyBeAppliedWithinEntities = members()
                .that().areAnnotatedWith(DefinesIdentity.class)
                .should().beDeclaredInClassesThat(are(annotatedWith(Entity.class)));
    }
}
