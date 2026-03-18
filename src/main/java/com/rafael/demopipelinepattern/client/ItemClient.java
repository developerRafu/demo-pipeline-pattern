package com.rafael.demopipelinepattern.client;

import com.rafael.demopipelinepattern.helper.SleepHelper;
import com.rafael.demopipelinepattern.models.response.CategoryResponse;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import com.rafael.demopipelinepattern.models.response.PriceResponse;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class ItemClient {

  public ItemResponse getById(final String id) {
    final ItemResponse itemResponse =
        ItemResponse.builder()
            .id(id)
            .name("item name")
            .description("Some item description")
            .categoryResponse(
                CategoryResponse.builder()
                    .id("categoryId01")
                    .name("category name")
                    .description("some category description")
                    .build())
            .price(PriceResponse.builder().price(BigDecimal.valueOf(100)).build())
            .build();
    return SleepHelper.sleep("ItemClient", itemResponse);
  }
}
