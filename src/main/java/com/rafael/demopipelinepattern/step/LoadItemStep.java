package com.rafael.demopipelinepattern.step;

import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.StepDefinition;
import com.rafael.demopipelinepattern.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class LoadItemStep implements AsyncStep {

    private final ItemService itemService;

    @Override
    public void run(final Context context) {
        context.setItem(itemService.getById(context.getId()));
    }

    @Override
    public StepDefinition definition() {
        return StepDefinition.LOAD_ITEM;
    }
}
