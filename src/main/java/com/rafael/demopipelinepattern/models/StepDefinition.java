package com.rafael.demopipelinepattern.models;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum StepDefinition {
  PRE_LOAD(0),
  LOAD_ITEM(1),
  LOAD_INVENTORY(2),
  LOAD_PROMOTION(3),
  RESPONSE_MAPPING(4),
  POST_MAPPING_RULES(5);
  private final Integer order;
}
