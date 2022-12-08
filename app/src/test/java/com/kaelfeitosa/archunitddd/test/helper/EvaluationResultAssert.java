package com.kaelfeitosa.archunitddd.test.helper;

import com.tngtech.archunit.lang.EvaluationResult;
import org.assertj.core.api.AbstractAssert;

import static java.lang.String.format;

public class EvaluationResultAssert extends AbstractAssert<EvaluationResultAssert, EvaluationResult> {
    public static EvaluationResultAssert assertThat(EvaluationResult actual) {
        return new EvaluationResultAssert(actual);
    }

    public EvaluationResultAssert(EvaluationResult actual) {
        super(actual, EvaluationResultAssert.class);
    }

    public EvaluationResultAssert isInvalid() {
        isNotNull();
        if (!actual.hasViolation()) {
            failWithMessage("Expected to be invalid, but there was no violations.");
        }
        return this;
    }

    public EvaluationResultAssert isValid() {
        isNotNull();
        if (actual.hasViolation()) {
            var details = actual.getFailureReport().getDetails();
            failWithMessage(format("Expected to be valid, but there was violations. %s", details));
        }
        return this;
    }
}
