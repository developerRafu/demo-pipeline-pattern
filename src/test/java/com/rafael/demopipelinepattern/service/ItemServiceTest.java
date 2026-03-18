package com.rafael.demopipelinepattern.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafael.demopipelinepattern.client.ItemClient;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("ItemService")
class ItemServiceTest {

  @Mock private ItemClient itemClient;

  @InjectMocks private ItemService itemService;

  @Test
  @DisplayName("given_itemId_when_getById_then_delegateToItemClient")
  void given_itemId_when_getById_then_delegateToItemClient() {
    // given
    final String itemId = "item123";
    final ItemResponse mockResponse = ItemResponse.builder().id(itemId).name("Test Item").build();
    when(itemClient.getById(itemId)).thenReturn(mockResponse);

    // when
    final ItemResponse result = itemService.getById(itemId);

    // then
    assertNotNull(result);
    assertEquals(itemId, result.getId());
    verify(itemClient).getById(itemId);
  }

  @Test
  @DisplayName("given_itemId_when_getById_then_returnItemResponseFromClient")
  void given_itemId_when_getById_then_returnItemResponseFromClient() {
    // given
    final String itemId = "item456";
    final ItemResponse mockResponse =
        ItemResponse.builder().id(itemId).name("Another Item").build();
    when(itemClient.getById(itemId)).thenReturn(mockResponse);

    // when
    final ItemResponse result = itemService.getById(itemId);

    // then
    assertEquals("Another Item", result.getName());
  }
}
