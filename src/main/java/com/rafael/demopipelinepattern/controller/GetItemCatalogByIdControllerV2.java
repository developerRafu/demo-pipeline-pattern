package com.rafael.demopipelinepattern.controller;

import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.service.CatalogServiceV2;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/v2/catalog")
@RestController
@RequiredArgsConstructor
public class GetItemCatalogByIdControllerV2 {
  private final CatalogServiceV2 catalogServiceV2;

  @GetMapping("/{id}")
  public ResponseEntity<CatalogResponse> getItemCatalogById(@PathVariable String id) {
    return ResponseEntity.ok(catalogServiceV2.get(id));
  }
}
