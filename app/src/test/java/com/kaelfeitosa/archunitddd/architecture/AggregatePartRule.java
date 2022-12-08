package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.core.domain.Dependency;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;

import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.getAggregateRootClass;
import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.getBoundedContext;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static java.lang.String.format;

public class AggregatePartRule {
    public static class C1OnlyEntitiesAndValueObjectsMayBeAggregateParts {
        @ArchTest
        public static final ArchRule onlyEntitiesAndValueObjectsMayBeAggregateParts = classes()
                .that().areAnnotatedWith(AggregatePart.class)
                .should().beAnnotatedWith(Entity.class)
                .orShould().beAnnotatedWith(ValueObject.class);
    }

    public static class C2AssignedAggregateRootMustHaveAggregateRootStereotype {
        @ArchTest
        public static final ArchRule entitiesAndValueObjectsAreAggregateParts = classes()
                .that().areAnnotatedWith(AggregatePart.class)
                .should(onlyHaveAggregateRootWithAggregateRootStereotype());
    }

    public static class C3NoIncomingAssociationsFromOutsideTheAggregate {
        @ArchTest
        public static final ArchRule noIncomingAssociationsFromOutsideTheAggregate = classes()
                .that().areAnnotatedWith(AggregatePart.class)
                .should(onlyHaveDependentClassInSameAggregateRoot());
    }

    public static class C4MusBeInSameBoundedContextAsAggregateRoot {
        @ArchTest
        public static final ArchRule mustBeInSameBoundedContextAsAggregateRoot = classes()
                .that().areAnnotatedWith(AggregatePart.class)
                .should(resideInSameBoundedContextAsHisAggregateRoot());
    }

    private static ArchCondition<? super JavaClass> resideInSameBoundedContextAsHisAggregateRoot() {
        return new ArchCondition<>("reside in same bounded context as his aggregate root") {
            @Override
            public void check(JavaClass aggregatePartJavaClass, ConditionEvents events) {
                boolean notInSamePackage = aggregatePartJavaClass.tryGetAnnotationOfType(AggregatePart.class)
                        .map(AggregatePart::aggregateRoot)
                        .filter(aClass -> aggregatePartJavaClass.getPackage().containsClass(aClass))
                        .isEmpty();

                if (notInSamePackage) {
                    events.add(violated(aggregatePartJavaClass,
                            format("Aggregate part %s is not in same package as Aggregate root class", aggregatePartJavaClass.getName())));
                }

                var optAggregateRootClass = getAggregateRootClass(aggregatePartJavaClass);
                if (optAggregateRootClass.isEmpty()) {
                    return;
                }

                var optAggregatePartBoundedContext = getBoundedContext(aggregatePartJavaClass);
                if (optAggregatePartBoundedContext.isEmpty()) {
                    events.add(violated(aggregatePartJavaClass,
                            format("Aggregate part %s does not have defined bounded context", aggregatePartJavaClass.getName())));
                }
                var aggregateRootClass = optAggregateRootClass.get();
                var optAggregateRootBoundedContext = getBoundedContext(aggregateRootClass);
                if (optAggregateRootBoundedContext.isEmpty()) {
                    events.add(violated(aggregatePartJavaClass,
                            format("Aggregate root %s does not have defined bounded context", aggregatePartJavaClass.getName())));
                }

                if (optAggregatePartBoundedContext.isEmpty() || optAggregateRootBoundedContext.isEmpty()) {
                    return;
                }

                var aggregatePartBoundedContext = optAggregatePartBoundedContext.get();
                var aggregateRootBoundedContext = optAggregateRootBoundedContext.get();

                if (!aggregatePartBoundedContext.getName().equals(aggregateRootBoundedContext.getName())) {
                    events.add(violated(aggregatePartJavaClass,
                            format("Aggregate part %s have bounded context %s that is different of aggregate root %s that have bounded context %s",
                                    aggregatePartJavaClass.getName(),
                                    aggregatePartBoundedContext.getName(),
                                    aggregateRootClass.getName(),
                                    aggregateRootBoundedContext.getName())));
                }
            }
        };
    }

    private static ArchCondition<JavaClass> onlyHaveDependentClassInSameAggregateRoot() {
        return new ArchCondition<>("only have dependent class in same aggregate root") {
            @Override
            public void check(JavaClass aggregatePartJavaClass, ConditionEvents events) {
                var aggregateRootClass = aggregatePartJavaClass.tryGetAnnotationOfType(AggregatePart.class).map(AggregatePart::aggregateRoot);
                if (aggregateRootClass.isEmpty()) {
                    return;
                }

                aggregatePartJavaClass.getDirectDependenciesToSelf().stream()
                        .map(Dependency::getOriginClass)
                        .filter(originClass -> !isSameAggregate(aggregatePartJavaClass, originClass))
                        .forEach(originClass -> events.add(violated(originClass,
                                format("Aggregate part %s with aggregate root %s have dependent class %s that is not same aggregate", aggregatePartJavaClass.getName(),  aggregateRootClass.get().getName(), originClass.getName()))));
            }
        };
    }

    private static boolean isSameAggregate(JavaClass clazz, JavaClass otherClass) {
        var aggregateRootClass = getAggregateRootClass(clazz);
        var otherAggregateRootClass = getAggregateRootClass(otherClass);
        if (aggregateRootClass.isPresent() && otherAggregateRootClass.isPresent()) {
            return aggregateRootClass.get().equals(otherAggregateRootClass.get());
        }
        return false;
    }

    private static ArchCondition<JavaClass> onlyHaveAggregateRootWithAggregateRootStereotype() {
        return new ArchCondition<>("only have aggregate root with aggregate root stereotype") {
            @Override
            public void check(JavaClass javaClass, ConditionEvents events) {
                var aggregateRootClass = getAggregateRootClass(javaClass);
                if (aggregateRootClass.isEmpty()) {
                    return;
                }
                if (aggregateRootClass.get().isAnnotatedWith(AggregateRoot.class)) {
                    return;
                }

                events.add(violated(javaClass,
                        format("Class %s is not annotated with @%s", aggregateRootClass.get().getName(), AggregatePart.class.getName())));
            }
        };
    }
}
