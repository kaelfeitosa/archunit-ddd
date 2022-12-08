package com.kaelfeitosa.archunitddd.architecture.helper;

import com.kaelfeitosa.archunitddd.architecture.AggregatePart;
import com.kaelfeitosa.archunitddd.architecture.AggregateRoot;
import com.kaelfeitosa.archunitddd.architecture.BoundedContext;
import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaAnnotation;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaPackage;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ConditionEvents;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.tngtech.archunit.lang.SimpleConditionEvent.violated;
import static java.lang.String.format;

public class TestUtils {

    public static JavaClasses importClasses(Class<?>... classes) {
        List<Class<?>> declaredClasses = Arrays.stream(classes)
                .map(Class::getDeclaredClasses)
                .flatMap(Arrays::stream)
                .collect(Collectors.toList());
        return new ClassFileImporter().importClasses(declaredClasses);
    }

    public static JavaClasses importClasses(String... packages) {
        return new ClassFileImporter().importPackages(packages);
    }

    public static ArchCondition<? super JavaClass> haveOnlyOperationsAndAtLeastOne() {
        return new ArchCondition<>("have only operations and at least one") {
            @Override
            public void check(JavaClass entityClass, ConditionEvents events) {
                entityClass.getFields().stream()
                        .filter(javaField -> !javaField.getName().contains("this$"))
                        .forEach(javaField -> events.add(violated(entityClass, format("There is property. name: %s", javaField.getName()))));

                if (!entityClass.getMethods().isEmpty()) return;

                events.add(violated(entityClass, "There is no operation."));
            }
        };
    }

    public static DescribedPredicate<? super JavaAnnotation<?>> anyOf(Class<?>... annotations) {
        return new DescribedPredicate<>("otherStereotypes") {
            @Override
            public boolean test(JavaAnnotation<?> javaAnnotation) {
                var annotationRawType = javaAnnotation.getRawType().reflect();
                return Arrays.asList(annotations).contains(annotationRawType);
            }
        };
    }

    public static DescribedPredicate<? super JavaClass> areAny() {
        return new DescribedPredicate<JavaClass>("are any") {
            @Override
            public boolean test(JavaClass javaClass) {
                return true;
            }
        };
    }

    public static ArchCondition<? super JavaClass> doAnything() {
        return new ArchCondition<>("nothing") {
            @Override
            public void check(JavaClass item, ConditionEvents events) {

            }
        };
    }

    public static Optional<JavaPackage> getBoundedContext(JavaPackage javaPackage) {
        Optional<JavaPackage> aPackageOpt = Optional.of(javaPackage);
        while (aPackageOpt.isPresent()) {
            JavaPackage aPackage = aPackageOpt.get();
            boolean isBoundedContext = aPackage.isAnnotatedWith(BoundedContext.class);
            if (isBoundedContext) return aPackageOpt;

            aPackageOpt = aPackage.getParent();
        }
        return Optional.empty();
    }

    public static Optional<JavaPackage> getBoundedContext(JavaClass aggregatePartJavaClass) {
        return getBoundedContext(aggregatePartJavaClass.getPackage());
    }

    public static Optional<JavaClass> getAggregateRootClass(JavaClass javaClass) {
        if (javaClass.isAnnotatedWith(AggregateRoot.class)) {
            return Optional.of(javaClass);
        } else if (javaClass.isAnnotatedWith(AggregatePart.class)) {
            return javaClass.tryGetAnnotationOfType(AggregatePart.class)
                    .map(AggregatePart::aggregateRoot)
                    .filter(aClass -> javaClass.getPackage().containsClass(aClass))
                    .map(aClass -> javaClass.getPackage().getClass(aClass));
        } else {
            return Optional.empty();
        }
    }
}
