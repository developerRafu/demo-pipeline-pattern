package com.rafael.demopipelinepattern.models.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryResponse {
  private String id;
  private String itemId;
  private Integer quantity;
  private Boolean available;
}
