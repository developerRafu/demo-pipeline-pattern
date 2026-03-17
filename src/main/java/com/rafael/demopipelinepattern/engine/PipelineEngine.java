package com.rafael.demopipelinepattern.engine;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.step.AsyncStep;
import com.rafael.demopipelinepattern.step.Step;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class PipelineEngine {
    private final List<Step> steps;

    public PipelineEngine(List<Step> steps) {
        this.steps = steps.stream().sorted(Comparator.comparing(s -> s.definition().getOrder())).toList();
    }

    public void execute(final Context context) {
        for (Step step : steps) {
            if (step.shouldRun(context)) {
                runStep(context, step);
            }
        }
    }

    private static void runStep(Context context, Step step) {
        if (step instanceof AsyncStep) {
            context.addFuture(step.definition(), CompletableFuture.runAsync(() -> step.run(context)));
        } else {
            step.run(context);
        }
    }
}
