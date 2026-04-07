package com.rafael.demopipelinepattern.service;

import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import com.rafael.demopipelinepattern.models.response.MetaDataResponse;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import java.util.concurrent.CompletableFuture;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CatalogService {
  private final ItemService itemService;
  private final InventoryService inventoryService;
  private final PromotionService promotionService;

  public CatalogResponse get(final String itemId) {
    CompletableFuture<ItemResponse> itemFuture =
        CompletableFuture.supplyAsync(() -> itemService.getById(itemId));

    CompletableFuture<InventoryResponse> inventoryFuture =
        CompletableFuture.supplyAsync(() -> inventoryService.getByItemId(itemId));

    CompletableFuture<PromotionResponse> promotionFuture =
        CompletableFuture.supplyAsync(() -> promotionService.getByItemId(itemId));

    return CompletableFuture.allOf(itemFuture, inventoryFuture, promotionFuture)
        .thenApply(
            v -> {
              final ItemResponse item = itemFuture.join();
              final InventoryResponse inventory = inventoryFuture.join();
              final PromotionResponse promotion = promotionFuture.join();

              validateItem(item);
              validateInventory(inventory);
              validatePromotion(promotion);

              final CatalogResponse catalogResponse = new CatalogResponse();
              catalogResponse.setItem(item);
              catalogResponse.setInventory(inventory);
              catalogResponse.setPromotion(promotion);
              catalogResponse.setMetadata(new MetaDataResponse());

              populateMetadata(catalogResponse);

              return catalogResponse;
            })
        .join();
  }

  private void populateMetadata(final CatalogResponse catalogResponse) {}

  private void validatePromotion(final PromotionResponse promotion) {}

  private void validateInventory(final InventoryResponse inventory) {}

  private void validateItem(final ItemResponse item) {}
}
