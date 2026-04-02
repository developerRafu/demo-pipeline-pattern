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
import java.math.RoundingMode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PromotionRuleTest {

  private PromotionRule promotionRule;
  private Context context;

  @BeforeEach
  void setUp() {
    promotionRule = new PromotionRule();
    context = new Context("test-item-id");
  }

  @Test
  void givenContextWithPromotion_whenShouldRun_thenReturnTrue() {
    // Given
    ItemResponse item =
        ItemResponse.builder()
            .id("item-1")
            .name("Test Item")
            .price(PriceResponse.builder().price(BigDecimal.valueOf(100)).build())
            .build();
    PromotionResponse promotion =
        PromotionResponse.builder().id("promo-1").itemId("item-1").percent(BigDecimal.TEN).build();
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .item(item)
            .promotion(promotion)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    boolean shouldRun = promotionRule.shouldRun(context);

    // Then
    assertTrue(shouldRun);
  }

  @Test
  void givenContextWithoutPromotion_whenShouldRun_thenReturnFalse() {
    // Given
    ItemResponse item =
        ItemResponse.builder()
            .id("item-1")
            .name("Test Item")
            .price(PriceResponse.builder().price(BigDecimal.valueOf(100)).build())
            .build();
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .item(item)
            .promotion(null)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    boolean shouldRun = promotionRule.shouldRun(context);

    // Then
    assertFalse(shouldRun);
  }

  @Test
  void givenContextWithPromotion_whenRun_thenShouldCalculatePrices() {
    // Given
    BigDecimal basePrice = BigDecimal.valueOf(100);
    BigDecimal promotionPercent = BigDecimal.valueOf(10);
    ItemResponse item =
        ItemResponse.builder()
            .id("item-1")
            .name("Test Item")
            .price(PriceResponse.builder().price(basePrice).build())
            .build();
    PromotionResponse promotion =
        PromotionResponse.builder()
            .id("promo-1")
            .itemId("item-1")
            .percent(promotionPercent)
            .build();
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .item(item)
            .promotion(promotion)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    promotionRule.run(context);

    // Then
    BigDecimal expectedPromotionalPrice =
        basePrice.multiply(promotionPercent).divide(BigDecimal.valueOf(100), RoundingMode.CEILING);
    BigDecimal expectedDiscountPrice = basePrice.subtract(expectedPromotionalPrice);

    assertEquals(
        expectedPromotionalPrice, context.getCatalogResponse().getMetadata().getPromotionalPrice());
    assertEquals(
        expectedDiscountPrice, context.getCatalogResponse().getMetadata().getDiscountPrice());
  }

  @Test
  void givenContextWithZeroPromotion_whenRun_thenShouldCalculateZeroPrices() {
    // Given
    BigDecimal basePrice = BigDecimal.valueOf(100);
    BigDecimal promotionPercent = BigDecimal.ZERO;
    ItemResponse item =
        ItemResponse.builder()
            .id("item-1")
            .name("Test Item")
            .price(PriceResponse.builder().price(basePrice).build())
            .build();
    PromotionResponse promotion =
        PromotionResponse.builder()
            .id("promo-1")
            .itemId("item-1")
            .percent(promotionPercent)
            .build();
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .item(item)
            .promotion(promotion)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    promotionRule.run(context);

    // Then
    assertEquals(BigDecimal.ZERO, context.getCatalogResponse().getMetadata().getPromotionalPrice());
    assertEquals(basePrice, context.getCatalogResponse().getMetadata().getDiscountPrice());
  }
}
