package com.rafael.demopipelinepattern.validation.postload;

import com.rafael.demopipelinepattern.models.Context;

public interface ResponseRule {
  default boolean shouldRun(final Context context) {
    return true;
  }

  void run(final Context context);
}
