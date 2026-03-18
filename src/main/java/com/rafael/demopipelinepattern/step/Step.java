package com.rafael.demopipelinepattern.step;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import java.util.Collections;
import java.util.List;

public interface Step {
  void run(final Context context);

  default boolean shouldRun(final Context context) {
    return true;
  }

  StepDefinition definition();

  default List<StepDefinition> dependsOn() {
    return Collections.emptyList();
  }

  default boolean required() {
    return false;
  }
}
