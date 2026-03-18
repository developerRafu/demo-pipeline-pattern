package com.rafael.demopipelinepattern.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("ResponseMappingStep")
class ResponseMappingStepTest {

  private final ResponseMappingStep step = new ResponseMappingStep();

  @Test
  @DisplayName("given_contextWithResponses_when_run_then_mapToCatalogResponse")
  void given_contextWithResponses_when_run_then_mapToCatalogResponse() {
    // given
    final Context context = new Context("item123");
    final ItemResponse item = ItemResponse.builder().id("item123").name("Test Item").build();
    final PromotionResponse promotion =
        PromotionResponse.builder().id("promo1").itemId("item123").percent(BigDecimal.TEN).build();

    context.setItem(item);
    context.setPromotion(promotion);

    // when
    step.run(context);

    // then
    assertNotNull(context.getCatalogResponse());
    assertEquals(item, context.getCatalogResponse().getItem());
    assertEquals(promotion, context.getCatalogResponse().getPromotion());
  }

  @Test
  @DisplayName("given_responseMappingStep_when_definition_then_returnResponseMappingDefinition")
  void given_responseMappingStep_when_definition_then_returnResponseMappingDefinition() {
    // when
    final StepDefinition definition = step.definition();

    // then
    assertEquals(StepDefinition.RESPONSE_MAPPING, definition);
  }

  @Test
  @DisplayName("given_responseMappingStep_when_dependsOn_then_returnLoadStepDependencies")
  void given_responseMappingStep_when_dependsOn_then_returnLoadStepDependencies() {
    // when
    final List<StepDefinition> dependencies = step.dependsOn();

    // then
    assertNotNull(dependencies);
    assertEquals(3, dependencies.size());
    assertEquals(StepDefinition.LOAD_ITEM, dependencies.get(0));
    assertEquals(StepDefinition.LOAD_INVENTORY, dependencies.get(1));
    assertEquals(StepDefinition.LOAD_PROMOTION, dependencies.get(2));
  }
}
