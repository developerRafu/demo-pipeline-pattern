package com.rafael.demopipelinepattern.mapper;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.models.response.MetaDataResponse;

public class CatalogResponseMapper {

  private CatalogResponseMapper() {}

  public static CatalogResponse toCatalogResponse(final Context context) {
    return CatalogResponse.builder()
        .item(context.getItem())
        .promotion(context.getPromotion())
        .inventory(context.getInventory())
        .metadata(buildMetadata())
        .build();
  }

  private static MetaDataResponse buildMetadata() {
    return MetaDataResponse.builder().build();
  }
}
