package com.rafael.demopipelinepattern.step;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;

public interface Step {
    void run(final Context context);
    default boolean shouldRun(final Context context){
        return true;
    }
    StepDefinition definition();
}
