package com.rafael.demopipelinepattern.client;

import com.rafael.demopipelinepattern.helper.SleepHelper;
import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import org.springframework.stereotype.Component;

@Component
public class InventoryClient {

  public InventoryResponse getByItemId(final String itemId) {
    final InventoryResponse inventoryResponse =
        InventoryResponse.builder()
            .id("inventoryId01")
            .itemId(itemId)
            .quantity(10)
            .available(Boolean.TRUE)
            .build();
    return SleepHelper.sleep("InventoryClient", inventoryResponse);
  }
}
