package com.rafael.demopipelinepattern.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafael.demopipelinepattern.exception.BadRequest;
import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.Message;
import com.rafael.demopipelinepattern.models.MessageModule;
import com.rafael.demopipelinepattern.models.MessageType;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.validation.postload.ResponseRule;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PostMappingStep")
class PostMappingStepTest {

  @Mock private ResponseRule rule1;
  @Mock private ResponseRule rule2;

  @InjectMocks private PostMappingStep postMappingStep;

  @Test
  @DisplayName("given_noRules_when_run_then_noErrorsThrown")
  void given_noRules_when_run_then_noErrorsThrown() {
    // given
    final PostMappingStep step = new PostMappingStep(Collections.emptyList());
    final Context context = new Context("test-id");

    // when & then
    step.run(context);
  }

  @Test
  @DisplayName("given_rulesWithoutErrors_when_run_then_executesRules")
  void given_rulesWithoutErrors_when_run_then_executesRules() {
    // given
    when(rule1.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);
    when(rule2.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);

    final PostMappingStep step = new PostMappingStep(List.of(rule1, rule2));
    final Context context = new Context("test-id");

    // when
    step.run(context);

    // then
    verify(rule1).run(context);
    verify(rule2).run(context);
  }

  @Test
  @DisplayName("given_ruleWithShouldRunFalse_when_run_then_ruleNotExecuted")
  void given_ruleWithShouldRunFalse_when_run_then_ruleNotExecuted() {
    // given
    when(rule1.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(false);

    final PostMappingStep step = new PostMappingStep(List.of(rule1));
    final Context context = new Context("test-id");

    // when
    step.run(context);

    // then
    verify(rule1, never()).run(context);
  }

  @Test
  @DisplayName("given_contextWithErrors_when_run_then_throwBadRequest")
  void given_contextWithErrors_when_run_then_throwBadRequest() {
    // given
    when(rule1.shouldRun(org.mockito.ArgumentMatchers.any())).thenReturn(true);

    final PostMappingStep step = new PostMappingStep(List.of(rule1));
    final Context context = new Context("test-id");
    context.addError(
        Message.builder()
            .text("Rule error")
            .type(MessageType.ERROR)
            .module(MessageModule.ENGINE_MODULE)
            .build());

    // when & then
    assertThrows(BadRequest.class, () -> step.run(context));
  }

  @Test
  @DisplayName("given_postMappingStep_when_definition_then_returnPostMappingDefinition")
  void given_postMappingStep_when_definition_then_returnPostMappingDefinition() {
    // when
    final StepDefinition definition = postMappingStep.definition();

    // then
    assertEquals(StepDefinition.POST_MAPPING_RULES, definition);
  }

  @Test
  @DisplayName("given_postMappingStep_when_required_then_returnTrue")
  void given_postMappingStep_when_required_then_returnTrue() {
    // when
    final boolean required = postMappingStep.required();

    // then
    assertTrue(required);
  }

  @Test
  @DisplayName("given_postMappingStep_when_dependsOn_then_returnResponseMappingDependency")
  void given_postMappingStep_when_dependsOn_then_returnResponseMappingDependency() {
    // when
    final List<StepDefinition> dependencies = postMappingStep.dependsOn();

    // then
    assertEquals(1, dependencies.size());
    assertEquals(StepDefinition.RESPONSE_MAPPING, dependencies.get(0));
  }
}
