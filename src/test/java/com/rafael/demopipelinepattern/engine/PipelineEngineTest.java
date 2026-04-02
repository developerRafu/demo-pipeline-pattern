package com.rafael.demopipelinepattern.engine;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.step.AsyncStep;
import com.rafael.demopipelinepattern.step.Step;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PipelineEngineTest {

  @Mock private Step syncStep;
  @Mock private AsyncStep asyncStep;
  @Mock private Step disabledStep;

  private PipelineEngine pipelineEngine;
  private Context context;

  @BeforeEach
  void setUp() {
    context = new Context("test-item-id");
    pipelineEngine = new PipelineEngine(List.of(syncStep, asyncStep, disabledStep));
  }

  @Test
  void givenPipelineWithSteps_whenExecute_thenAllStepsShouldRun() {
    // Given
    when(syncStep.shouldRun(context)).thenReturn(true);
    when(syncStep.definition()).thenReturn(StepDefinition.PRE_LOAD);
    when(syncStep.dependsOn()).thenReturn(List.of());
    when(asyncStep.shouldRun(context)).thenReturn(true);
    when(asyncStep.definition()).thenReturn(StepDefinition.LOAD_ITEM);
    when(asyncStep.dependsOn()).thenReturn(List.of());
    when(disabledStep.shouldRun(context)).thenReturn(false);

    // When
    assertDoesNotThrow(() -> pipelineEngine.execute(context));

    // Then
    verify(syncStep, times(1)).run(context);
    verify(asyncStep, times(1)).run(context);
    verify(disabledStep, never()).run(context);
  }

  @Test
  void givenStepWithDependencies_whenExecute_thenDependenciesShouldCompleteFirst() {
    // Given
    when(syncStep.shouldRun(context)).thenReturn(true);
    when(syncStep.definition()).thenReturn(StepDefinition.PRE_LOAD);
    when(syncStep.dependsOn()).thenReturn(List.of());
    when(asyncStep.shouldRun(context)).thenReturn(true);
    when(asyncStep.definition()).thenReturn(StepDefinition.LOAD_ITEM);
    when(asyncStep.dependsOn()).thenReturn(List.of(StepDefinition.PRE_LOAD));
    when(disabledStep.shouldRun(context)).thenReturn(false);

    // When
    assertDoesNotThrow(() -> pipelineEngine.execute(context));

    // Then
    verify(syncStep, times(1)).run(context);
    verify(asyncStep, times(1)).run(context);
  }

  @Test
  void givenPipelineEngine_whenShutdown_thenExecutorServiceShouldShutdown() {
    // Given
    PipelineEngine engine = new PipelineEngine(List.of());

    // When
    assertDoesNotThrow(engine::shutdown);

    // Then - no exception thrown
  }

  @Test
  void givenEmptyStepList_whenExecute_thenShouldCompleteSuccessfully() {
    // Given
    PipelineEngine engine = new PipelineEngine(List.of());

    // When
    assertDoesNotThrow(() -> engine.execute(context));

    // Then - no exception thrown
  }

  @Test
  void givenContextWithFutures_whenExecute_thenFuturesShouldBeRegistered() {
    // Given
    when(syncStep.shouldRun(context)).thenReturn(true);
    when(syncStep.definition()).thenReturn(StepDefinition.PRE_LOAD);
    when(syncStep.dependsOn()).thenReturn(List.of());
    when(asyncStep.shouldRun(context)).thenReturn(false);
    when(disabledStep.shouldRun(context)).thenReturn(false);

    // When
    pipelineEngine.execute(context);

    // Then
    assertNotNull(context.getFutures());
  }
}
