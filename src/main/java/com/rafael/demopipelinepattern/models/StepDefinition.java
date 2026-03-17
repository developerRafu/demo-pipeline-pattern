package com.rafael.demopipelinepattern.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StepDefinition {
    LOAD_ITEM(0),
    LOAD_INVENTORY(1),
    LOAD_PROMOTION(2);
    private final Integer order;
}
