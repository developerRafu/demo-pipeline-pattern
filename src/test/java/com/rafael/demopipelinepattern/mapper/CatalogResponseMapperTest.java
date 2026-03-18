package com.rafael.demopipelinepattern.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("CatalogResponseMapper")
class CatalogResponseMapperTest {

  @Test
  @DisplayName("given_contextWithItem_when_toCatalogResponse_then_returnCatalogWithItem")
  void given_contextWithItem_when_toCatalogResponse_then_returnCatalogWithItem() {
    // given
    final Context context = new Context("item123");
    final ItemResponse item = ItemResponse.builder().id("item123").name("Test Item").build();
    context.setItem(item);

    // when
    final CatalogResponse result = CatalogResponseMapper.toCatalogResponse(context);

    // then
    assertNotNull(result);
    assertEquals(item, result.getItem());
  }

  @Test
  @DisplayName("given_contextWithPromotion_when_toCatalogResponse_then_returnCatalogWithPromotion")
  void given_contextWithPromotion_when_toCatalogResponse_then_returnCatalogWithPromotion() {
    // given
    final Context context = new Context("item123");
    final PromotionResponse promotion =
        PromotionResponse.builder().id("promo1").itemId("item123").percent(BigDecimal.TEN).build();
    context.setPromotion(promotion);

    // when
    final CatalogResponse result = CatalogResponseMapper.toCatalogResponse(context);

    // then
    assertNotNull(result);
    assertEquals(promotion, result.getPromotion());
  }

  @Test
  @DisplayName("given_contextWithInventory_when_toCatalogResponse_then_returnCatalogWithInventory")
  void given_contextWithInventory_when_toCatalogResponse_then_returnCatalogWithInventory() {
    // given
    final Context context = new Context("item123");
    final InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv1")
            .itemId("item123")
            .quantity(10)
            .available(true)
            .build();
    context.setInventory(inventory);

    // when
    final CatalogResponse result = CatalogResponseMapper.toCatalogResponse(context);

    // then
    assertNotNull(result);
    assertEquals(inventory, result.getInventory());
  }

  @Test
  @DisplayName("given_contextWithAllFields_when_toCatalogResponse_then_returnCompleteCatalog")
  void given_contextWithAllFields_when_toCatalogResponse_then_returnCompleteCatalog() {
    // given
    final Context context = new Context("item123");
    final ItemResponse item = ItemResponse.builder().id("item123").name("Test Item").build();
    final PromotionResponse promotion =
        PromotionResponse.builder().id("promo1").itemId("item123").build();
    final InventoryResponse inventory =
        InventoryResponse.builder().id("inv1").itemId("item123").quantity(10).build();

    context.setItem(item);
    context.setPromotion(promotion);
    context.setInventory(inventory);

    // when
    final CatalogResponse result = CatalogResponseMapper.toCatalogResponse(context);

    // then
    assertNotNull(result);
    assertEquals(item, result.getItem());
    assertEquals(promotion, result.getPromotion());
    assertEquals(inventory, result.getInventory());
    assertNotNull(result.getMetadata());
  }
}
