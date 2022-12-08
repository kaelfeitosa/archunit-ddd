package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;

import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

public class AggregateRootRule {
    public static class C5OnlyEntitiesMayBeAggregateRoots {
        @ArchTest
        public static final ArchRule onlyEntitiesMayBeAggregateRoots = classes()
                .that().areAnnotatedWith(AggregateRoot.class)
                .should().beAnnotatedWith(Entity.class);
    }

    public static class C6AggregateMustContainAtLeastOnePart {
        @ArchTest
        public static final ArchRule aggregateMustContainAtLeastOnePart = classes()
                .that().areAnnotatedWith(AggregateRoot.class)
                .should(haveUnlessOneAggregatePart());

        private static ArchCondition<? super JavaClass> haveUnlessOneAggregatePart() {
            return new ArchCondition<>("have unless one aggregate part") {
                @Override
                public void check(JavaClass aggregateRootClass, ConditionEvents events) {
                    var aggregatePartFieldsCount = aggregateRootClass.getAllFields().stream()
                            .filter(javaField -> javaField.getRawType().isAnnotatedWith(AggregatePart.class)).count();

                    if (aggregatePartFieldsCount > 0) return;

                    events.add(violated(aggregateRootClass, "There is no aggregate part"));
                }
            };
        }
    }
}
