package com.rafael.demopipelinepattern.models;

import com.rafael.demopipelinepattern.exception.BadRequest;
import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.models.response.InventoryResponse;
import com.rafael.demopipelinepattern.models.response.ItemResponse;
import com.rafael.demopipelinepattern.models.response.PromotionResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Getter
@Setter
@RequiredArgsConstructor
public class Context {
    private final String id;
    private CatalogResponse catalogResponse;
    private ItemResponse item;
    private PromotionResponse promotion;
    private InventoryResponse inventory;
    private final List<Message> errors = new ArrayList<>();
    private final Map<StepDefinition, CompletableFuture<Void>> futures = new EnumMap<>(StepDefinition.class);

    public void addFuture(final StepDefinition definition, final CompletableFuture<Void> future) {
        futures.put(definition, future);
    }

    public CompletableFuture<Void> getFutureRequired(final StepDefinition definition) {
        CompletableFuture<Void> future = futures.get(definition);
        if (future == null) {
            throw new IllegalStateException("Dependency future not registered for step: " + definition);
        }
        return future;
    }

    public void addError(final Message message) {
        errors.add(message);
    }

    public boolean hasErrors() {
        return !errors.isEmpty();
    }

    public BadRequest throwBadRequest(final String message) {
        return new BadRequest(errors, message);
    }
}
