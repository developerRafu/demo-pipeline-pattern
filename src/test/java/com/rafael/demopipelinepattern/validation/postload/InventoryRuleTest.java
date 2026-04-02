package com.rafael.demopipelinepattern.validation.postload;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import com.rafael.demopipelinepattern.models.response.MetaDataResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InventoryRuleTest {

  private InventoryRule inventoryRule;
  private Context context;

  @BeforeEach
  void setUp() {
    inventoryRule = new InventoryRule();
    context = new Context("test-item-id");
  }

  @Test
  void givenContextWithInventory_whenShouldRun_thenReturnTrue() {
    // Given
    InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv-1")
            .itemId("item-1")
            .quantity(10)
            .available(true)
            .build();
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .inventory(inventory)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    boolean shouldRun = inventoryRule.shouldRun(context);

    // Then
    assertTrue(shouldRun);
  }

  @Test
  void givenContextWithoutInventory_whenShouldRun_thenReturnFalse() {
    // Given
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .inventory(null)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    boolean shouldRun = inventoryRule.shouldRun(context);

    // Then
    assertFalse(shouldRun);
  }

  @Test
  void givenInventoryWithQuantityAndAvailable_whenRun_thenShouldSetAvailableTrue() {
    // Given
    InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv-1")
            .itemId("item-1")
            .quantity(10)
            .available(true)
            .build();
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .inventory(inventory)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    inventoryRule.run(context);

    // Then
    assertTrue(context.getCatalogResponse().getMetadata().isAvailable());
  }

  @Test
  void givenInventoryWithZeroQuantity_whenRun_thenShouldSetAvailableFalse() {
    // Given
    InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv-1")
            .itemId("item-1")
            .quantity(0)
            .available(true)
            .build();
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .inventory(inventory)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    inventoryRule.run(context);

    // Then
    assertFalse(context.getCatalogResponse().getMetadata().isAvailable());
  }

  @Test
  void givenInventoryWithNegativeQuantity_whenRun_thenShouldSetAvailableFalse() {
    // Given
    InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv-1")
            .itemId("item-1")
            .quantity(-5)
            .available(true)
            .build();
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .inventory(inventory)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    inventoryRule.run(context);

    // Then
    assertFalse(context.getCatalogResponse().getMetadata().isAvailable());
  }

  @Test
  void givenInventoryWithAvailableFalse_whenRun_thenShouldSetAvailableFalse() {
    // Given
    InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv-1")
            .itemId("item-1")
            .quantity(10)
            .available(false)
            .build();
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .inventory(inventory)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    inventoryRule.run(context);

    // Then
    assertFalse(context.getCatalogResponse().getMetadata().isAvailable());
  }

  @Test
  void givenInventoryWithNullQuantity_whenRun_thenShouldSetAvailableFalse() {
    // Given
    InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv-1")
            .itemId("item-1")
            .quantity(null)
            .available(true)
            .build();
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .inventory(inventory)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    inventoryRule.run(context);

    // Then
    assertFalse(context.getCatalogResponse().getMetadata().isAvailable());
  }

  @Test
  void givenInventoryWithNullAvailable_whenRun_thenShouldSetAvailableFalse() {
    // Given
    InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv-1")
            .itemId("item-1")
            .quantity(10)
            .available(null)
            .build();
    CatalogResponse catalogResponse =
        CatalogResponse.builder()
            .inventory(inventory)
            .metadata(MetaDataResponse.builder().build())
            .build();
    context.setCatalogResponse(catalogResponse);

    // When
    inventoryRule.run(context);

    // Then
    assertFalse(context.getCatalogResponse().getMetadata().isAvailable());
  }
}
