package com.rafael.demopipelinepattern.service;

import com.rafael.demopipelinepattern.client.ItemClient;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemClient itemClient;

    public ItemResponse getById(final String id) {
        return itemClient.getById(id);
    }
}
