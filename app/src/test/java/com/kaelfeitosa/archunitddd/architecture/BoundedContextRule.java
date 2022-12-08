package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;

import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.*;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static java.lang.String.format;

public class BoundedContextRule {
    public static class C24MustNotHaveModuleStereotype {
        @ArchTest
        public static final ArchRule mustNotHaveModuleStereotype = classes()
                .that().areAnnotatedWith(anyOf(
                        AggregatePart.class,
                        AggregateRoot.class,
                        Entity.class,
                        Repository.class,
                        Service.class,
                        Spec.class,
                        ValueObject.class
                )).should(notBeInBoundedContextThatIsModule());

        private static ArchCondition<? super JavaClass> notBeInBoundedContextThatIsModule() {
            return new ArchCondition<>("not be in bounded context that is module") {
                @Override
                public void check(JavaClass javaClass, ConditionEvents events) {
                    var optionalBoundedContext = getBoundedContext(javaClass);
                    if (optionalBoundedContext.isEmpty()) return;

                    var boundedContext = optionalBoundedContext.get();
                    if (boundedContext.isAnnotatedWith(Module.class))
                        events.add(violated(javaClass, format("Reside in package that is bounded context and module. Package: %s", boundedContext.getName())));
                }
            };
        }
    }

    public static class C25MustNotBeNested {
        @ArchTest
        public static final ArchRule mustNotBeNested = classes()
                .that().areAnnotatedWith(anyOf(
                        AggregatePart.class,
                        AggregateRoot.class,
                        Entity.class,
                        Repository.class,
                        Service.class,
                        Spec.class,
                        ValueObject.class
                )).should(notBeInNestedBoundedContext());

        private static ArchCondition<? super JavaClass> notBeInNestedBoundedContext() {
            return new ArchCondition<>("not be in nested bounded context") {
                @Override
                public void check(JavaClass javaClass, ConditionEvents events) {
                    var optBoundedContext = getBoundedContext(javaClass);
                    if (optBoundedContext.isEmpty()) return;

                    var boundedContext = optBoundedContext.get();
                    var optParentPackage = boundedContext.getParent();
                    if (optParentPackage.isEmpty()) return;

                    getBoundedContext(optParentPackage.get())
                            .ifPresent(aPackage -> addEvent(javaClass, events, boundedContext, aPackage));
                }

                private void addEvent(JavaClass javaClass, ConditionEvents events, JavaPackage boundedContext, JavaPackage aPackage) {
                    events.add(violated(javaClass,
                            format("Reside in packages that are nested bounded contexts. enclosing package: %s, nested package: %s",
                                    aPackage.getName(), boundedContext.getName())));
                }
            };
        }
    }
}
