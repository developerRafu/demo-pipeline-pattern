package com.rafael.demopipelinepattern.step;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoadPromotionStep implements AsyncStep {

  private final PromotionService promotionService;

  @Override
  public void run(final Context context) {
    context.setPromotion(promotionService.getByItemId(context.getId()));
  }

  @Override
  public StepDefinition definition() {
    return StepDefinition.LOAD_PROMOTION;
  }
}
