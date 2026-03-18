package com.rafael.demopipelinepattern.controller;

import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.service.CatalogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/catalog")
@RestController
@RequiredArgsConstructor
public class GetItemCatalogByIdController {
  private final CatalogService catalogService;

  @GetMapping("/{id}")
  public ResponseEntity<CatalogResponse> getItemCatalogById(@PathVariable String id) {
    return ResponseEntity.ok(catalogService.get(id));
  }
}
