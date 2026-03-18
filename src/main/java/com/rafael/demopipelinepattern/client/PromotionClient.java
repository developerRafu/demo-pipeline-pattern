package com.rafael.demopipelinepattern.client;

import com.rafael.demopipelinepattern.helper.SleepHelper;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import java.math.BigDecimal;
import org.springframework.stereotype.Component;

@Component
public class PromotionClient {

  public PromotionResponse getByItemId(final String itemId) {
    final PromotionResponse promotionResponse =
        PromotionResponse.builder()
            .id("DEFAULT_PROMOTION_ID")
            .itemId(itemId)
            .description("DEFAULT_PROMOTION_DESCRIPTION")
            .percent(BigDecimal.valueOf(5))
            .build();
    return SleepHelper.sleep("PromotionClient", promotionResponse);
  }
}
