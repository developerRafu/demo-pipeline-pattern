package com.rafael.demopipelinepattern.engine;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.Message;
import com.rafael.demopipelinepattern.models.MessageModule;
import com.rafael.demopipelinepattern.models.MessageType;
import com.rafael.demopipelinepattern.step.AsyncStep;
import com.rafael.demopipelinepattern.step.Step;
import jakarta.annotation.PreDestroy;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class PipelineEngine {
  private final List<Step> steps;
  private final ExecutorService asyncExecutor;

  public PipelineEngine(List<Step> steps) {
    this.steps =
        steps.stream().sorted(Comparator.comparing(s -> s.definition().getOrder())).toList();
    this.asyncExecutor = Executors.newVirtualThreadPerTaskExecutor();
  }

  @PreDestroy
  public void shutdown() {
    asyncExecutor.shutdown();
  }

  public void execute(final Context context) {
    for (Step step : steps) {
      if (step.shouldRun(context)) {
        scheduleStep(context, step);
      }
    }
    waitForCompletion(context);
  }

  private void scheduleStep(final Context context, final Step step) {
    CompletableFuture<Void> dependencies = dependenciesFor(context, step);
    if (step instanceof AsyncStep) {
      scheduleAsyncStep(context, step, dependencies);
    } else {
      scheduleSyncStep(context, step, dependencies);
    }
  }

  private void waitForCompletion(final Context context) {
    CompletableFuture.allOf(context.getFutures().values().toArray(new CompletableFuture[0])).join();
  }

  private CompletableFuture<Void> dependenciesFor(final Context context, final Step step) {
    return CompletableFuture.allOf(
        step.dependsOn().stream()
            .map(context::getFutureRequired)
            .toArray(CompletableFuture[]::new));
  }

  private void scheduleAsyncStep(
      final Context context, final Step step, final CompletableFuture<Void> dependencies) {
    CompletableFuture<Void> future =
        CompletableFuture.runAsync(
            () -> {
              dependencies.join();
              run(context, step);
            },
            asyncExecutor);

    context.addFuture(step.definition(), future);
  }

  private void scheduleSyncStep(
      final Context context, final Step step, final CompletableFuture<Void> dependencies) {
    CompletableFuture<Void> future = new CompletableFuture<>();
    context.addFuture(step.definition(), future);
    CompletableFuture.runAsync(
        () -> {
          try {
            dependencies.join();
            run(context, step);
            future.complete(null);
          } catch (Exception e) {
            log.error("Error running step {}", step.definition(), e);
            if (step.required()) {
              future.completeExceptionally(e);
            } else {
              context.addError(
                  Message.builder()
                      .text("Error running step")
                      .type(MessageType.WARNING)
                      .module(MessageModule.ENGINE_MODULE)
                      .details(List.of(e.getMessage()))
                      .build());
              future.complete(null);
            }
          }
        },
        asyncExecutor);
  }

  private void run(Context context, Step step) {
    step.run(context);
  }
}
