package com.rafael.demopipelinepattern.client;

import com.rafael.demopipelinepattern.helper.SleepHelper;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PromotionClient {

    public PromotionResponse getByItemId(final String itemId) {
        SleepHelper.sleep("PromotionClient");
        return PromotionResponse.builder()
                .id("DEFAULT_PROMOTION_ID")
                .itemId(itemId)
                .description("DEFAULT_PROMOTION_DESCRIPTION")
                .percent(BigDecimal.valueOf(5))
                .build();
    }
}
