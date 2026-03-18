package com.rafael.demopipelinepattern.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

import com.rafael.demopipelinepattern.engine.PipelineEngine;
import com.rafael.demopipelinepattern.models.Context;
import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@DisplayName("CatalogService")
class CatalogServiceTest {

  @Mock private PipelineEngine engine;

  @InjectMocks private CatalogService catalogService;

  @Test
  @DisplayName("given_itemId_when_get_then_executeEngineAndReturnCatalogResponse")
  void given_itemId_when_get_then_executeEngineAndReturnCatalogResponse() {
    // given
    final String itemId = "item123";
    final CatalogResponse expectedCatalog = CatalogResponse.builder().build();

    // Mock the engine to set the catalog response in context
    org.mockito.Mockito.doAnswer(
            invocation -> {
              Context context = invocation.getArgument(0);
              context.setCatalogResponse(expectedCatalog);
              return null;
            })
        .when(engine)
        .execute(org.mockito.ArgumentMatchers.any(Context.class));

    // when
    final CatalogResponse result = catalogService.get(itemId);

    // then
    assertNotNull(result);
    assertEquals(expectedCatalog, result);

    // Verify engine was called with context containing the itemId
    ArgumentCaptor<Context> contextCaptor = ArgumentCaptor.forClass(Context.class);
    verify(engine).execute(contextCaptor.capture());
    assertEquals(itemId, contextCaptor.getValue().getId());
  }

  @Test
  @DisplayName("given_differentItemId_when_get_then_delegateToEngine")
  void given_differentItemId_when_get_then_delegateToEngine() {
    // given
    final String itemId = "item456";
    final CatalogResponse expectedCatalog = CatalogResponse.builder().build();

    org.mockito.Mockito.doAnswer(
            invocation -> {
              Context context = invocation.getArgument(0);
              context.setCatalogResponse(expectedCatalog);
              return null;
            })
        .when(engine)
        .execute(org.mockito.ArgumentMatchers.any(Context.class));

    // when
    catalogService.get(itemId);

    // then
    verify(engine).execute(org.mockito.ArgumentMatchers.any(Context.class));
  }
}
