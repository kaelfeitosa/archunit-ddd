package com.kaelfeitosa.archunitddd.architecture;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.ArchTests;

@AnalyzeClasses(packages = "com.kaelfeitosa.archunitddd.domain")
public class ArchRules {
    @ArchTest
    static final ArchTests C1OnlyEntitiesAndValueObjectsMayBeAggregateParts = ArchTests.in(AggregatePartRule.C1OnlyEntitiesAndValueObjectsMayBeAggregateParts.class);

    @ArchTest
    static final ArchTests C2AssignedAggregateRootMustHaveAggregateRootStereotype = ArchTests.in(AggregatePartRule.C2AssignedAggregateRootMustHaveAggregateRootStereotype.class);

    @ArchTest
    static final ArchTests C3NoIncomingAssociationsFromOutsideTheAggregate = ArchTests.in(AggregatePartRule.C3NoIncomingAssociationsFromOutsideTheAggregate.class);

    @ArchTest
    static final ArchTests C4MusBeInSameBoundedContextAsAggregateRoot = ArchTests.in(AggregatePartRule.C4MusBeInSameBoundedContextAsAggregateRoot.class);

    @ArchTest
    static final ArchTests C5OnlyEntitiesMayBeAggregateRoots = ArchTests.in(AggregateRootRule.C5OnlyEntitiesMayBeAggregateRoots.class);

    @ArchTest
    static final ArchTests C6AggregateMustContainAtLeastOnePart = ArchTests.in(AggregateRootRule.C6AggregateMustContainAtLeastOnePart.class);

    @ArchTest
    static final ArchTests C7OneOperationOrAtLeastOnePropertyDefinesTheIdentity = ArchTests.in(EntityRule.C7OneOperationOrAtLeastOnePropertyDefinesTheIdentity.class);

    @ArchTest
    static final ArchTests C8ClassHasNoOtherStereotypes = ArchTests.in(RepositoryRule.C8ClassHasNoOtherStereotypes.class);

    @ArchTest
    static final ArchTests C9ClassContainsOnlyOperationsAndAtLeastOne = ArchTests.in(RepositoryRule.C9ClassContainsOnlyOperationsAndAtLeastOne.class);

    @ArchTest
    static final ArchTests C10OutgoingAssociationsMustPointToEntitiesOrValueObjects = ArchTests.in(RepositoryRule.C10OutgoingAssociationsMustPointToEntitiesOrValueObjects.class);

    @ArchTest
    static final ArchTests C11ClassHasNoOtherStereotypes = ArchTests.in(ServiceRule.C11ClassHasNoOtherStereotypes.class);

    @ArchTest
    static final ArchTests C12ClassHasNoOtherStereotypes = ArchTests.in(ServiceRule.C12ClassContainsOnlyOperationsAndAtLeastOne.class);

    @ArchTest
    static final ArchTests C13ClassHasNoOtherStereotypes = ArchTests.in(SpecRule.C13ClassHasNoOtherStereotypes.class);

    @ArchTest
    static final ArchTests C14ClassContainsAtLeastOneValidationOperation = ArchTests.in(SpecRule.C14ClassContainsAtLeastOneValidationOperation.class);

    @ArchTest
    static final ArchTests C15AtLeastOneDomainObjectIsSpecified = ArchTests.in(SpecRule.C15AtLeastOneDomainObjectIsSpecified.class);

    @ArchTest
    static final ArchTests C16ValidationOperationHasParameterTypedAsSpecifiedObject = ArchTests.in(SpecRule.C16ValidationOperationHasParameterTypedAsSpecifiedObject.class);

    @ArchTest
    static final ArchTests C17MustNotBeSpecificationValidationOrIdentityOperation = ArchTests.in(ClosureRule.C17MustNotBeSpecificationValidationOrIdentityOperation.class);

    @ArchTest
    static final ArchTests C18ReturnParameterTypeMustConformInputParameterType = ArchTests.in(ClosureRule.C18ReturnParameterTypeMustConformInputParameterType.class);

    @ArchTest
    static final ArchTests C19MustNotBeSpecificationValidationOperation = ArchTests.in(DefinesIdentityRule.C19MustNotBeSpecificationValidationOperation.class);

    @ArchTest
    static final ArchTests C20MayOnlyBeAppliedWithinEntities = ArchTests.in(DefinesIdentityRule.C20MayOnlyBeAppliedWithinEntities.class);

    @ArchTest
    static final ArchTests C21OperationMustHaveAReturnParameter = ArchTests.in(SideEffectFreeRule.C21OperationMustHaveAReturnParameter.class);

    @ArchTest
    static final ArchTests C22MustHaveBooleanTypedReturnParameter = ArchTests.in(ValidatesSpecRule.C22MustHaveBooleanTypedReturnParameter.class);

    @ArchTest
    static final ArchTests C23MayOnlyBeAppliedWithinSpecifications = ArchTests.in(ValidatesSpecRule.C23MayOnlyBeAppliedWithinSpecifications.class);

    @ArchTest
    static final ArchTests C24MustNotHaveModuleStereotype = ArchTests.in(BoundedContextRule.C24MustNotHaveModuleStereotype.class);

    @ArchTest
    static final ArchTests C25MustNotBeNested = ArchTests.in(BoundedContextRule.C25MustNotBeNested.class);
}
