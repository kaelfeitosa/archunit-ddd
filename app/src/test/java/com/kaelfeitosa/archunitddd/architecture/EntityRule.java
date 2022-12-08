package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;

import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static java.lang.String.format;

public class EntityRule {
    public static class C7OneOperationOrAtLeastOnePropertyDefinesTheIdentity {
        @ArchTest
        public static final ArchRule oneOperationOrAtLeastOnePropertyDefinesTheIdentity = classes()
                .that().areAnnotatedWith(Entity.class)
                .should(haveFieldOrMethodThatDefinesTheIdentity());

        private static ArchCondition<? super JavaClass> haveFieldOrMethodThatDefinesTheIdentity() {
            return new ArchCondition<>("have field or method that defines the identity") {
                @Override
                public void check(JavaClass entityClass, ConditionEvents events) {
                    var definesIdentityPropertyCount = entityClass.getAllMembers().stream()
                            .filter(javaField -> javaField.isAnnotatedWith(DefinesIdentity.class)).count();

                    if (definesIdentityPropertyCount > 0) return;

                    events.add(violated(entityClass, format("Entity %s has no property that defines identity", entityClass.getName())));
                }
            };
        }
    }
}
