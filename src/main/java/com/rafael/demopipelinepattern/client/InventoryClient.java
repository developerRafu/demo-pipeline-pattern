package com.rafael.demopipelinepattern.client;

import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import org.springframework.stereotype.Component;

@Component
public class InventoryClient {

    public InventoryResponse getByItemId(final String itemId) {
        return InventoryResponse.builder()
                .id("DEFAULT_INVENTORY_ID")
                .itemId(itemId)
                .quantity(0)
                .available(Boolean.TRUE)
                .build();
    }
}
