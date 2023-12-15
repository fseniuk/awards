package com.raspberry.awards.model;

import java.util.Set;

public record CompleteResult(Set<IndividualResult> min, Set<IndividualResult> max) {
}
