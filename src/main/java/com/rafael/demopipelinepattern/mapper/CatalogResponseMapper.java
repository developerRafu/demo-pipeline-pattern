package com.rafael.demopipelinepattern.mapper;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.models.response.MetaDataResponse;

import java.math.BigDecimal;

public class CatalogResponseMapper {

    private CatalogResponseMapper() {
    }

    public static CatalogResponse toCatalogResponse(final Context context) {
        return CatalogResponse.builder()
                .item(context.getItem())
                .promotion(context.getPromotion())
                .inventory(context.getInventory())
                .metadata(buildMetadata(context))
                .build();
    }

    private static MetaDataResponse buildMetadata(final Context context) {

        return MetaDataResponse.builder()
                .build();
    }
}
