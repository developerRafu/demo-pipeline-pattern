package com.rafael.demopipelinepattern.validation.postload;

import com.rafael.demopipelinepattern.models.Context;
import org.springframework.stereotype.Component;

@Component
public class InventoryRule implements ResponseRule {

  @Override
  public boolean shouldRun(final Context context) {
    return context.getCatalogResponse() != null
        && context.getCatalogResponse().getInventory() != null;
  }

  @Override
  public void run(final Context context) {
    Integer quantity = context.getCatalogResponse().getInventory().getQuantity();
    Boolean available = context.getCatalogResponse().getInventory().getAvailable();

    boolean isAvailable = quantity != null && quantity > 0 && available != null && available;

    context.getCatalogResponse().getMetadata().setAvailable(isAvailable);
  }
}
