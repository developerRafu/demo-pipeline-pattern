package com.rafael.demopipelinepattern.step;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.validation.postload.ResponseRule;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMappingStep implements Step {
  private final List<ResponseRule> rules;

  @Override
  public void run(final Context context) {
    rules.stream().filter(rule -> rule.shouldRun(context)).forEach(rule -> rule.run(context));

    if (context.hasErrors()) {
      throw context.throwBadRequest("Error running rules");
    }
  }

  @Override
  public StepDefinition definition() {
    return StepDefinition.POST_MAPPING_RULES;
  }

  @Override
  public List<StepDefinition> dependsOn() {
    return List.of(StepDefinition.RESPONSE_MAPPING);
  }
}
