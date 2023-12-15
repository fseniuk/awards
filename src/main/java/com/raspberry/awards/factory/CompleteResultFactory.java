package com.raspberry.awards.factory;

import com.raspberry.awards.model.CompleteResult;
import com.raspberry.awards.model.IndividualResult;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class CompleteResultFactory {

    public CompleteResult create(Set<IndividualResult> minResult, Set<IndividualResult> maxResult) {
        return new CompleteResult(minResult, maxResult);
    }
}
