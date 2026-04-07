package com.rafael.demopipelinepattern.client;

import com.rafael.demopipelinepattern.helper.SleepHelper;
import com.rafael.demopipelinepattern.models.response.PointResponse;
import org.springframework.stereotype.Component;

@Component
public class PointClient {

  public PointResponse getByItemId(final String itemId) {
    SleepHelper.sleep("PointClient");
    return PointResponse.builder()
        .itemId(itemId)
        .quantity(100)
        .build();
  }
}
