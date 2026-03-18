package com.rafael.demopipelinepattern.service;

import com.rafael.demopipelinepattern.client.PromotionClient;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromotionService {

  private final PromotionClient promotionClient;

  public PromotionResponse getByItemId(final String itemId) {
    return promotionClient.getByItemId(itemId);
  }
}
