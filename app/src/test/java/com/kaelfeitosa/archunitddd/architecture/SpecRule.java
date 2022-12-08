package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.domain.JavaType;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import org.javatuples.KeyValue;

import java.util.Optional;
import java.util.stream.Collectors;

import static com.kaelfeitosa.archunitddd.architecture.helper.TestUtils.anyOf;
import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static java.lang.String.format;

public class SpecRule {
    public static class C13ClassHasNoOtherStereotypes {
        @ArchTest
        public static final ArchRule classHasNoOtherStereotypes = classes()
                .that().areAnnotatedWith(Spec.class)
                .should().notBeAnnotatedWith(anyOf(
                        AggregatePart.class,
                        AggregateRoot.class,
                        Entity.class,
                        Repository.class,
                        Service.class,
                        ValueObject.class));
    }

    public static class C14ClassContainsAtLeastOneValidationOperation {
        @ArchTest
        public static final ArchRule classContainsAtLeastOneValidationOperation = classes()
                .that().areAnnotatedWith(Spec.class)
                .should(containsAtLeastOneValidationOperation());
    }

    public static class C15AtLeastOneDomainObjectIsSpecified {
        @ArchTest
        public static final ArchRule atLeastOneDomainObjectIsSpecified = classes()
                .that().areAnnotatedWith(Spec.class)
                .should(specifiesDomainObject());
    }

    public static class C16ValidationOperationHasParameterTypedAsSpecifiedObject {
        @ArchTest
        public static final ArchRule validationOperationHasParameterTypedAsSpecifiedObject = classes()
                .that().areAnnotatedWith(Spec.class)
                .should(haveParameterTypedAsSpecifiedObject());
    }

    public static ArchCondition<? super JavaClass> haveParameterTypedAsSpecifiedObject() {
        return new ArchCondition<>("specifies domain object") {
            @Override
            public void check(JavaClass entityClass, ConditionEvents events) {
                var specifiedClassOpt = getSpecifiedClass(entityClass);
                if (specifiedClassOpt.isEmpty()) return;

                Class<?> specifiedClass = specifiedClassOpt.get();

                entityClass.getMethods()
                        .stream().filter(javaMethod -> hasNotSpecifiedType(specifiedClass, javaMethod))
                        .map(javaMethod -> new KeyValue<>(javaMethod, javaMethod.getParameterTypes()))
                        .forEach(keyValue -> events.add(violated(entityClass, format("Validation operation %s has parameter type %s different of specified %s",
                                specifiedClass.getName(), keyValue.getKey().getName(), keyValue.getValue().stream().map(JavaType::getName).collect(Collectors.joining(", "))))));

            }

            private boolean hasNotSpecifiedType(Class<?> specifiedClass, JavaMethod javaMethod) {
                return javaMethod.getParameterTypes().stream()
                        .map(JavaType::toErasure)
                        .map(JavaClass::reflect)
                        .noneMatch(aClass -> aClass.equals(specifiedClass));
            }
        };
    }

    public static ArchCondition<? super JavaClass> specifiesDomainObject() {
        return new ArchCondition<>("specifies domain object") {
            @Override
            public void check(JavaClass entityClass, ConditionEvents events) {
                var specifiedClassOpt = getSpecifiedClass(entityClass);

                if(specifiedClassOpt.isEmpty()) return;

                Class<?> specifiedClass = specifiedClassOpt.get();
                if (!specifiedClass.isAnnotationPresent(Entity.class))
                    events.add(violated(entityClass, format("Specified class %s is not a domain object", specifiedClass.getName())));
            }
        };
    }

    public static ArchCondition<? super JavaClass> containsAtLeastOneValidationOperation() {
        return new ArchCondition<>("contains at least one validation operation") {
            @Override
            public void check(JavaClass entityClass, ConditionEvents events) {
                var noneMatch = entityClass.getMethods().stream()
                        .noneMatch(javaMethod -> javaMethod.isAnnotatedWith(ValidatesSpec.class));

                if (noneMatch)
                    events.add(violated(entityClass, "There is no valitation operation."));
            }
        };
    }

    private static Optional<Class<?>> getSpecifiedClass(JavaClass javaClass) {
        if (javaClass.isAnnotatedWith(Spec.class)) {
            return javaClass.tryGetAnnotationOfType(Spec.class).map(Spec::specified);
        } else {
            return Optional.empty();
        }
    }
}
