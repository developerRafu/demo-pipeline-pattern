package com.rafael.demopipelinepattern.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafael.demopipelinepattern.client.PromotionClient;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import java.math.BigDecimal;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("PromotionService")
class PromotionServiceTest {

  @Mock private PromotionClient promotionClient;

  @InjectMocks private PromotionService promotionService;

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_delegateToPromotionClient")
  void given_itemId_when_getByItemId_then_delegateToPromotionClient() {
    // given
    final String itemId = "item123";
    final PromotionResponse mockResponse =
        PromotionResponse.builder().id("promo1").itemId(itemId).percent(BigDecimal.TEN).build();
    when(promotionClient.getByItemId(itemId)).thenReturn(mockResponse);

    // when
    final PromotionResponse result = promotionService.getByItemId(itemId);

    // then
    assertNotNull(result);
    assertEquals(itemId, result.getItemId());
    verify(promotionClient).getByItemId(itemId);
  }

  @Test
  @DisplayName("given_itemId_when_getByItemId_then_returnPromotionResponseFromClient")
  void given_itemId_when_getByItemId_then_returnPromotionResponseFromClient() {
    // given
    final String itemId = "item456";
    final PromotionResponse mockResponse =
        PromotionResponse.builder()
            .id("promo2")
            .itemId(itemId)
            .percent(BigDecimal.valueOf(15))
            .description("Test Promotion")
            .build();
    when(promotionClient.getByItemId(itemId)).thenReturn(mockResponse);

    // when
    final PromotionResponse result = promotionService.getByItemId(itemId);

    // then
    assertEquals("Test Promotion", result.getDescription());
    assertEquals(15, result.getPercent().intValue());
  }
}
