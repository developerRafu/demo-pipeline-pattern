package com.rafael.demopipelinepattern.validation.postload;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import com.rafael.demopipelinepattern.models.response.MetaDataResponse;
import com.rafael.demopipelinepattern.models.response.PriceResponse;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("PromotionRule")
class PromotionRuleTest {

  private final PromotionRule rule = new PromotionRule();

  @Test
  @DisplayName("given_contextWithNullCatalog_when_shouldRun_then_returnFalse")
  void given_contextWithNullCatalog_when_shouldRun_then_returnFalse() {
    // given
    final Context context = new Context("item123");

    // when
    final boolean result = rule.shouldRun(context);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("given_contextWithNullPromotion_when_shouldRun_then_returnFalse")
  void given_contextWithNullPromotion_when_shouldRun_then_returnFalse() {
    // given
    final Context context = new Context("item123");
    context.setCatalogResponse(CatalogResponse.builder().build());

    // when
    final boolean result = rule.shouldRun(context);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("given_contextWithNullItem_when_shouldRun_then_returnFalse")
  void given_contextWithNullItem_when_shouldRun_then_returnFalse() {
    // given
    final Context context = new Context("item123");
    final PromotionResponse promotion =
        PromotionResponse.builder().id("promo1").itemId("item123").percent(BigDecimal.TEN).build();
    context.setCatalogResponse(CatalogResponse.builder().promotion(promotion).build());

    // when
    final boolean result = rule.shouldRun(context);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("given_contextWithNullPrice_when_shouldRun_then_returnFalse")
  void given_contextWithNullPrice_when_shouldRun_then_returnFalse() {
    // given
    final Context context = new Context("item123");
    final PromotionResponse promotion =
        PromotionResponse.builder().id("promo1").itemId("item123").percent(BigDecimal.TEN).build();
    final ItemResponse item = ItemResponse.builder().id("item123").name("Test Item").build();
    context.setCatalogResponse(CatalogResponse.builder().promotion(promotion).item(item).build());

    // when
    final boolean result = rule.shouldRun(context);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("given_validPromotion_when_shouldRun_then_returnTrue")
  void given_validPromotion_when_shouldRun_then_returnTrue() {
    // given
    final Context context = new Context("item123");
    final PromotionResponse promotion =
        PromotionResponse.builder().id("promo1").itemId("item123").percent(BigDecimal.TEN).build();
    final ItemResponse item =
        ItemResponse.builder()
            .id("item123")
            .name("Test Item")
            .price(PriceResponse.builder().price(BigDecimal.valueOf(100)).build())
            .build();
    context.setCatalogResponse(
        CatalogResponse.builder()
            .promotion(promotion)
            .item(item)
            .metadata(MetaDataResponse.builder().build())
            .build());

    // when
    final boolean result = rule.shouldRun(context);

    // then
    assertTrue(result);
  }

  @Test
  @DisplayName("given_validPromotion_when_run_then_calculatePromotionalAndDiscountPrice")
  void given_validPromotion_when_run_then_calculatePromotionalAndDiscountPrice() {
    // given
    final Context context = new Context("item123");
    final PromotionResponse promotion =
        PromotionResponse.builder().id("promo1").itemId("item123").percent(BigDecimal.TEN).build();
    final ItemResponse item =
        ItemResponse.builder()
            .id("item123")
            .name("Test Item")
            .price(PriceResponse.builder().price(BigDecimal.valueOf(100)).build())
            .build();
    context.setCatalogResponse(
        CatalogResponse.builder()
            .promotion(promotion)
            .item(item)
            .metadata(MetaDataResponse.builder().build())
            .build());

    // when
    rule.run(context);

    // then
    assertEquals(BigDecimal.TEN, context.getCatalogResponse().getMetadata().getPromotionalPrice());
    assertEquals(
        BigDecimal.valueOf(90), context.getCatalogResponse().getMetadata().getDiscountPrice());
  }
}
