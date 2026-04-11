package com.rafael.demopipelinepattern.service;

import com.rafael.demopipelinepattern.client.PointClient;
import com.rafael.demopipelinepattern.models.response.PointResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PointService {
  private final PointClient client;

  public PointResponse getByItemId(final String itemId) {
    return client.getByItemId(itemId);
  }
}
