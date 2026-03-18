package com.rafael.demopipelinepattern.validation.postload;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import com.rafael.demopipelinepattern.models.response.MetaDataResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("InventoryRule")
class InventoryRuleTest {

  private final InventoryRule rule = new InventoryRule();

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
  @DisplayName("given_contextWithNullInventory_when_shouldRun_then_returnFalse")
  void given_contextWithNullInventory_when_shouldRun_then_returnFalse() {
    // given
    final Context context = new Context("item123");
    context.setCatalogResponse(CatalogResponse.builder().build());

    // when
    final boolean result = rule.shouldRun(context);

    // then
    assertFalse(result);
  }

  @Test
  @DisplayName("given_contextWithValidInventory_when_shouldRun_then_returnTrue")
  void given_contextWithValidInventory_when_shouldRun_then_returnTrue() {
    // given
    final Context context = new Context("item123");
    final InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv1")
            .itemId("item123")
            .quantity(10)
            .available(true)
            .build();
    context.setCatalogResponse(
        CatalogResponse.builder()
            .inventory(inventory)
            .metadata(MetaDataResponse.builder().build())
            .build());

    // when
    final boolean result = rule.shouldRun(context);

    // then
    assertTrue(result);
  }

  @Test
  @DisplayName("given_validInventory_when_run_then_setAvailableTrue")
  void given_validInventory_when_run_then_setAvailableTrue() {
    // given
    final Context context = new Context("item123");
    final InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv1")
            .itemId("item123")
            .quantity(10)
            .available(true)
            .build();
    context.setCatalogResponse(
        CatalogResponse.builder()
            .inventory(inventory)
            .metadata(MetaDataResponse.builder().build())
            .build());

    // when
    rule.run(context);

    // then
    assertTrue(context.getCatalogResponse().getMetadata().isAvailable());
  }

  @Test
  @DisplayName("given_inventoryWithZeroQuantity_when_run_then_setAvailableFalse")
  void given_inventoryWithZeroQuantity_when_run_then_setAvailableFalse() {
    // given
    final Context context = new Context("item123");
    final InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv1")
            .itemId("item123")
            .quantity(0)
            .available(true)
            .build();
    context.setCatalogResponse(
        CatalogResponse.builder()
            .inventory(inventory)
            .metadata(MetaDataResponse.builder().build())
            .build());

    // when
    rule.run(context);

    // then
    assertFalse(context.getCatalogResponse().getMetadata().isAvailable());
  }

  @Test
  @DisplayName("given_inventoryWithAvailableFalse_when_run_then_setAvailableFalse")
  void given_inventoryWithAvailableFalse_when_run_then_setAvailableFalse() {
    // given
    final Context context = new Context("item123");
    final InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv1")
            .itemId("item123")
            .quantity(10)
            .available(false)
            .build();
    context.setCatalogResponse(
        CatalogResponse.builder()
            .inventory(inventory)
            .metadata(MetaDataResponse.builder().build())
            .build());

    // when
    rule.run(context);

    // then
    assertFalse(context.getCatalogResponse().getMetadata().isAvailable());
  }
}
