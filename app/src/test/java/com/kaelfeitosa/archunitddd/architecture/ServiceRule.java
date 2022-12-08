package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;

import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.*;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class ServiceRule {
    public static class C11ClassHasNoOtherStereotypes {
        @ArchTest
        public static final ArchRule classHasNoOtherStereotypes = classes()
                .that().areAnnotatedWith(Service.class)
                .should().notBeAnnotatedWith(anyOf(
                        AggregatePart.class,
                        AggregateRoot.class,
                        Entity.class,
                        Repository.class,
                        Spec.class,
                        ValueObject.class));
    }

    public static class C12ClassContainsOnlyOperationsAndAtLeastOne {
        @ArchTest
        public static final ArchRule classContainsOnlyOperationsAndAtLeastOne = classes()
                .that().areAnnotatedWith(Service.class)
                .should(haveOnlyOperationsAndAtLeastOne());
    }
}
