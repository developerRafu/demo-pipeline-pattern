package com.rafael.demopipelinepattern.engine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.step.Step;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PipelineEngine")
class PipelineEngineTest {

  @Mock private Step mockStep1;
  @Mock private Step mockStep2;

  @Test
  @DisplayName("given_emptyStepsList_when_execute_then_noErrorsOccur")
  void given_emptyStepsList_when_execute_then_noErrorsOccur() {
    // given
    final List<Step> steps = Collections.emptyList();
    final PipelineEngine engine = new PipelineEngine(steps);
    final Context context = new Context("test-id");

    // when
    engine.execute(context);

    // then
    assertNotNull(context);
    assertFalse(context.hasErrors());
  }

  @Test
  @DisplayName("given_singleStep_when_execute_then_stepIsExecuted")
  void given_singleStep_when_execute_then_stepIsExecuted() {
    // given
    when(mockStep1.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);
    when(mockStep1.definition()).thenReturn(StepDefinition.LOAD_ITEM);
    when(mockStep1.dependsOn()).thenReturn(Collections.emptyList());

    final List<Step> steps = List.of(mockStep1);
    final PipelineEngine engine = new PipelineEngine(steps);
    final Context context = new Context("test-id");

    // when
    engine.execute(context);

    // then
    verify(mockStep1).run(context);
  }

  @Test
  @DisplayName("given_multipleSteps_when_execute_then_stepsAreExecutedInOrder")
  void given_multipleSteps_when_execute_then_stepsAreExecutedInOrder() {
    // given
    when(mockStep1.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);
    when(mockStep1.definition()).thenReturn(StepDefinition.LOAD_ITEM);
    when(mockStep1.dependsOn()).thenReturn(Collections.emptyList());

    when(mockStep2.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);
    when(mockStep2.definition()).thenReturn(StepDefinition.LOAD_PROMOTION);
    when(mockStep2.dependsOn()).thenReturn(Collections.emptyList());

    final List<Step> steps = List.of(mockStep1, mockStep2);
    final PipelineEngine engine = new PipelineEngine(steps);
    final Context context = new Context("test-id");

    // when
    engine.execute(context);

    // then
    InOrder inOrder = inOrder(mockStep1, mockStep2);
    inOrder.verify(mockStep1).run(context);
    inOrder.verify(mockStep2).run(context);
  }

  @Test
  @DisplayName("given_stepWithShouldRunFalse_when_execute_then_stepIsNotExecuted")
  void given_stepWithShouldRunFalse_when_execute_then_stepIsNotExecuted() {
    // given
    when(mockStep1.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(false);

    final List<Step> steps = List.of(mockStep1);
    final PipelineEngine engine = new PipelineEngine(steps);
    final Context context = new Context("test-id");

    // when
    engine.execute(context);

    // then
    verify(mockStep1, never()).run(context);
  }

  @Test
  @DisplayName("given_contextWithId_when_execute_then_contextIsNotNull")
  void given_contextWithId_when_execute_then_contextIsNotNull() {
    // given
    final List<Step> steps = Collections.emptyList();
    final PipelineEngine engine = new PipelineEngine(steps);
    final Context context = new Context("test-id-123");

    // when
    engine.execute(context);

    // then
    assertNotNull(context);
    assertEquals("test-id-123", context.getId());
  }

  @Test
  @DisplayName("given_stepsSortedByOrder_when_execute_then_stepsExecuteInOrderSequence")
  void given_stepsSortedByOrder_when_execute_then_stepsExecuteInOrderSequence() {
    // given
    when(mockStep1.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);
    when(mockStep1.definition()).thenReturn(StepDefinition.LOAD_ITEM);
    when(mockStep1.dependsOn()).thenReturn(Collections.emptyList());

    when(mockStep2.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);
    when(mockStep2.definition()).thenReturn(StepDefinition.LOAD_PROMOTION);
    when(mockStep2.dependsOn()).thenReturn(Collections.emptyList());

    final List<Step> steps = List.of(mockStep2, mockStep1);
    final PipelineEngine engine = new PipelineEngine(steps);
    final Context context = new Context("test-id");

    // when
    engine.execute(context);

    // then
    InOrder inOrder = inOrder(mockStep1, mockStep2);
    inOrder.verify(mockStep1).run(context);
    inOrder.verify(mockStep2).run(context);
  }

  @Test
  @DisplayName("given_multipleStepsWithMixedShouldRun_when_execute_then_onlyEligibleStepsRun")
  void given_multipleStepsWithMixedShouldRun_when_execute_then_onlyEligibleStepsRun() {
    // given
    when(mockStep1.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);
    when(mockStep1.definition()).thenReturn(StepDefinition.LOAD_ITEM);
    when(mockStep1.dependsOn()).thenReturn(Collections.emptyList());

    when(mockStep2.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(false);
    when(mockStep2.definition()).thenReturn(StepDefinition.LOAD_PROMOTION);

    final List<Step> steps = List.of(mockStep1, mockStep2);
    final PipelineEngine engine = new PipelineEngine(steps);
    final Context context = new Context("test-id");

    // when
    engine.execute(context);

    // then
    verify(mockStep1).run(context);
    verify(mockStep2, never()).run(context);
  }
}
