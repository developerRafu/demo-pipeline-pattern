package com.rafael.demopipelinepattern.step;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import com.rafael.demopipelinepattern.service.PromotionService;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoadPromotionStep")
class LoadPromotionStepTest {

  @Mock private PromotionService promotionService;

  @InjectMocks private LoadPromotionStep loadPromotionStep;

  @Test
  @DisplayName("given_contextWithId_when_run_then_setPromotionInContext")
  void given_contextWithId_when_run_then_setPromotionInContext() {
    // given
    final String itemId = "item123";
    final Context context = new Context(itemId);
    final PromotionResponse mockPromotionResponse =
        PromotionResponse.builder().id("promo1").itemId(itemId).percent(BigDecimal.TEN).build();
    when(promotionService.getByItemId(itemId)).thenReturn(mockPromotionResponse);

    // when
    loadPromotionStep.run(context);

    // then
    assertNotNull(context.getPromotion());
    assertEquals(itemId, context.getPromotion().getItemId());
    verify(promotionService).getByItemId(itemId);
  }

  @Test
  @DisplayName("given_contextWithId_when_run_then_delegateToPromotionService")
  void given_contextWithId_when_run_then_delegateToPromotionService() {
    // given
    final String itemId = "item456";
    final Context context = new Context(itemId);
    final PromotionResponse mockPromotionResponse =
        PromotionResponse.builder()
            .id("promo2")
            .itemId(itemId)
            .percent(BigDecimal.valueOf(15))
            .description("Test Promotion")
            .build();
    when(promotionService.getByItemId(itemId)).thenReturn(mockPromotionResponse);

    // when
    loadPromotionStep.run(context);

    // then
    assertEquals("Test Promotion", context.getPromotion().getDescription());
  }

  @Test
  @DisplayName("given_loadPromotionStep_when_definition_then_returnLoadPromotionDefinition")
  void given_loadPromotionStep_when_definition_then_returnLoadPromotionDefinition() {
    // when
    final StepDefinition definition = loadPromotionStep.definition();

    // then
    assertEquals(StepDefinition.LOAD_PROMOTION, definition);
  }
}
