package com.rafael.demopipelinepattern.service;

import com.rafael.demopipelinepattern.engine.PipelineEngine;
import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CatalogServiceV2 {
  private final PipelineEngine engine;

  public CatalogResponse get(String id) {
    final Context context = new Context(id);
    engine.execute(context);
    return context.getCatalogResponse();
  }
}
