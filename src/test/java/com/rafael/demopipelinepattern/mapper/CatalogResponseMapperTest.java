package com.rafael.demopipelinepattern.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import com.rafael.demopipelinepattern.models.response.PriceResponse;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CatalogResponseMapperTest {

  private Context context;

  @BeforeEach
  void setUp() {
    context = new Context("test-item-id");
  }

  @Test
  void givenContextWithAllData_whenToCatalogResponse_thenShouldMapAllFields() {
    // Given
    ItemResponse item =
        ItemResponse.builder()
            .id("item-1")
            .name("Test Item")
            .description("Test Description")
            .price(PriceResponse.builder().price(BigDecimal.valueOf(100)).build())
            .build();
    PromotionResponse promotion =
        PromotionResponse.builder().id("promo-1").itemId("item-1").percent(BigDecimal.TEN).build();
    InventoryResponse inventory =
        InventoryResponse.builder()
            .id("inv-1")
            .itemId("item-1")
            .quantity(10)
            .available(true)
            .build();

    context.setItem(item);
    context.setPromotion(promotion);
    context.setInventory(inventory);

    // When
    CatalogResponse response = CatalogResponseMapper.toCatalogResponse(context);

    // Then
    assertNotNull(response);
    assertEquals(item, response.getItem());
    assertEquals(promotion, response.getPromotion());
    assertEquals(inventory, response.getInventory());
    assertNotNull(response.getMetadata());
  }

  @Test
  void givenContextWithoutItem_whenToCatalogResponse_thenShouldHaveNullItem() {
    // Given & When
    CatalogResponse response = CatalogResponseMapper.toCatalogResponse(context);

    // Then
    assertNotNull(response);
    assertNull(response.getItem());
  }

  @Test
  void givenContextWithoutPromotion_whenToCatalogResponse_thenShouldHaveNullPromotion() {
    // Given & When
    CatalogResponse response = CatalogResponseMapper.toCatalogResponse(context);

    // Then
    assertNotNull(response);
    assertNull(response.getPromotion());
  }

  @Test
  void givenContextWithoutInventory_whenToCatalogResponse_thenShouldHaveNullInventory() {
    // Given & When
    CatalogResponse response = CatalogResponseMapper.toCatalogResponse(context);

    // Then
    assertNotNull(response);
    assertNull(response.getInventory());
  }

  @Test
  void givenContextWithPartialData_whenToCatalogResponse_thenShouldMapOnlyAvailableData() {
    // Given
    ItemResponse item =
        ItemResponse.builder()
            .id("item-1")
            .name("Test Item")
            .description("Test Description")
            .build();
    context.setItem(item);

    // When
    CatalogResponse response = CatalogResponseMapper.toCatalogResponse(context);

    // Then
    assertNotNull(response);
    assertEquals(item, response.getItem());
    assertNull(response.getPromotion());
    assertNull(response.getInventory());
  }
}
