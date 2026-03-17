package com.rafael.demopipelinepattern.client;

import com.rafael.demopipelinepattern.models.response.CategoryResponse;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import com.rafael.demopipelinepattern.models.response.PriceResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ItemClient {

    public ItemResponse getById(final String id) {
        return ItemResponse.builder()
                .id(id)
                .name("DEFAULT_ITEM_NAME")
                .description("DEFAULT_ITEM_DESCRIPTION")
                .categoryResponse(CategoryResponse.builder()
                        .id("DEFAULT_CATEGORY_ID")
                        .name("DEFAULT_CATEGORY_NAME")
                        .description("DEFAULT_CATEGORY_DESCRIPTION")
                        .build())
                .price(PriceResponse.builder()
                        .price(BigDecimal.ZERO)
                        .build())
                .build();
    }
}
