package com.rafael.demopipelinepattern.service;

import com.rafael.demopipelinepattern.client.InventoryClient;
import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryClient inventoryClient;

    public InventoryResponse getByItemId(final String itemId) {
        return inventoryClient.getByItemId(itemId);
    }
}
