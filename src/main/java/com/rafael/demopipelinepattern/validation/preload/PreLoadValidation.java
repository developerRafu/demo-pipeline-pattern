package com.rafael.demopipelinepattern.validation.preload;

import com.rafael.demopipelinepattern.models.Context;

public interface PreLoadValidation {

  default boolean shouldRun(final Context context) {
    return true;
  }

  void run(final Context context);
}
