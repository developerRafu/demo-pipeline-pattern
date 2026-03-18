package com.rafael.demopipelinepattern.step;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.validation.preload.PreLoadValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PreLoadStep implements Step {
    private final List<PreLoadValidation> validations;

    @Override
    public void run(final Context context) {
        validations
                .stream()
                .filter(v -> v.shouldRun(context))
                .forEach(v -> v.run(context));

        if(context.hasErrors()){
            throw context.throwBadRequest("Error running validations");
        }
    }

    @Override
    public StepDefinition definition() {
        return StepDefinition.PRE_LOAD;
    }
}
