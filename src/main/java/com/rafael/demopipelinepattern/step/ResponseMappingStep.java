package com.rafael.demopipelinepattern.step;

import com.rafael.demopipelinepattern.mapper.CatalogResponseMapper;
import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
public class ResponseMappingStep implements Step {
    @Override
    public void run(final Context context) {
        log.info("Responses returned {} - {} - {}", context.getItem(), context.getPromotion(), context.getInventory());
        context.setCatalogResponse(CatalogResponseMapper.toCatalogResponse(context));
    }

    @Override
    public StepDefinition definition() {
        return StepDefinition.RESPONSE_MAPPING;
    }

    @Override
    public List<StepDefinition> dependsOn() {
        return List.of(StepDefinition.LOAD_ITEM, StepDefinition.LOAD_INVENTORY, StepDefinition.LOAD_PROMOTION);
    }
}
