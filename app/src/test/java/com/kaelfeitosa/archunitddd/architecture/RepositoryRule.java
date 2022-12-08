package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;

import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.*;
import static com.tngtech.archunit.core.domain.JavaClass.Predicates.*;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static java.lang.String.format;

public class RepositoryRule {
    public static class C8ClassHasNoOtherStereotypes {
        @ArchTest
        public static final ArchRule classHasNoOtherStereotypes = classes()
                .that().areAnnotatedWith(Repository.class)
                .should().notBeAnnotatedWith(anyOf(
                        AggregatePart.class,
                        AggregateRoot.class,
                        Entity.class,
                        Service.class,
                        Spec.class,
                        ValueObject.class));
    }

    public static class C9ClassContainsOnlyOperationsAndAtLeastOne {
        @ArchTest
        public static final ArchRule classContainsOnlyOperationsAndAtLeastOne = classes()
                .that().areAnnotatedWith(Repository.class)
                .should(haveOnlyOperationsAndAtLeastOne());
    }

    public static class C10OutgoingAssociationsMustPointToEntitiesOrValueObjects {
        @ArchTest
        public static final ArchRule outgoingAssociationsMustPointToEntitiesOrValueObjects = classes()
                .that().areAnnotatedWith(Repository.class)
                .should(onlyAccessClassThatAreAnnotatedWithEntityAndValueObject());
    }

    private static ArchCondition<? super JavaClass> onlyAccessClassThatAreAnnotatedWithEntityAndValueObject(Class<?>... valueObjectClass) {
        return new ArchCondition<>("only access class that are annotated with") {
            @Override
            public void check(JavaClass entityClass, ConditionEvents events) {
                entityClass.getDirectDependenciesFromSelf().stream()
                        .filter(dependency -> entityClass.getEnclosingClass().filter(javaClass -> dependency.getTargetClass().equals(javaClass)).isEmpty())
                        .filter(dependency -> !dependency.getTargetClass().getPackage().getName().startsWith("java."))
                        .filter(dependency -> !dependency.getTargetClass().isEquivalentTo(Repository.class))
                        .filter(dependency -> !dependency.getTargetClass().isAnnotatedWith(Repository.class))
                        .filter(dependency -> !dependency.getTargetClass().isAnnotatedWith(ValueObject.class))
                        .filter(dependency -> !dependency.getTargetClass().isAnnotatedWith(Entity.class))
                        .forEach(dependency -> events.add(violated(entityClass, format("There is dependency. target class: %s, description: %s", dependency.getTargetClass(), dependency.getDescription()))));
            }
        };
    }
}
