package com.rafael.demopipelinepattern.step;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoadInventoryStep implements AsyncStep {

    private final InventoryService inventoryService;

    @Override
    public void run(final Context context) {
        context.setInventory(inventoryService.getByItemId(context.getId()));
    }

    @Override
    public StepDefinition definition() {
        return StepDefinition.LOAD_INVENTORY;
    }

}
