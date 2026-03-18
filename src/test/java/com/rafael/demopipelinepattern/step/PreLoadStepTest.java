package com.rafael.demopipelinepattern.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafael.demopipelinepattern.exception.BadRequest;
import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.Message;
import com.rafael.demopipelinepattern.models.MessageModule;
import com.rafael.demopipelinepattern.models.MessageType;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.validation.preload.PreLoadValidation;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PreLoadStep")
class PreLoadStepTest {

  @Mock private PreLoadValidation validation1;
  @Mock private PreLoadValidation validation2;

  @InjectMocks private PreLoadStep preLoadStep;

  @Test
  @DisplayName("given_noValidations_when_run_then_noErrorsThrown")
  void given_noValidations_when_run_then_noErrorsThrown() {
    // given
    final PreLoadStep step = new PreLoadStep(Collections.emptyList());
    final Context context = new Context("test-id");

    // when & then
    step.run(context);
  }

  @Test
  @DisplayName("given_validationsWithoutErrors_when_run_then_executesValidations")
  void given_validationsWithoutErrors_when_run_then_executesValidations() {
    // given
    when(validation1.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);
    when(validation2.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);

    final PreLoadStep step = new PreLoadStep(List.of(validation1, validation2));
    final Context context = new Context("test-id");

    // when
    step.run(context);

    // then
    verify(validation1).run(context);
    verify(validation2).run(context);
  }

  @Test
  @DisplayName("given_validationWithShouldRunFalse_when_run_then_validationNotExecuted")
  void given_validationWithShouldRunFalse_when_run_then_validationNotExecuted() {
    // given
    when(validation1.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(false);

    final PreLoadStep step = new PreLoadStep(List.of(validation1));
    final Context context = new Context("test-id");

    // when
    step.run(context);

    // then
    verify(validation1, never()).run(context);
  }

  @Test
  @DisplayName("given_contextWithErrors_when_run_then_throwBadRequest")
  void given_contextWithErrors_when_run_then_throwBadRequest() {
    // given
    when(validation1.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);

    final PreLoadStep step = new PreLoadStep(List.of(validation1));
    final Context context = new Context("test-id");
    context.addError(
        Message.builder()
            .text("Validation error")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .build());

    // when & then
    assertThrows(BadRequest.class, () -> step.run(context));
  }

  @Test
  @DisplayName("given_preLoadStep_when_definition_then_returnPreLoadDefinition")
  void given_preLoadStep_when_definition_then_returnPreLoadDefinition() {
    // when
    final StepDefinition definition = preLoadStep.definition();

    // then
    assertEquals(StepDefinition.PRE_LOAD, definition);
  }
}
