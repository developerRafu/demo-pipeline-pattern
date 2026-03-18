package com.rafael.demopipelinepattern.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.rafael.demopipelinepattern.models.response.CatalogResponse;
import com.rafael.demopipelinepattern.service.CatalogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetItemCatalogByIdController")
class GetItemCatalogByIdControllerTest {

  @Mock private CatalogService catalogService;

  @InjectMocks private GetItemCatalogByIdController controller;

  @Test
  @DisplayName("given_itemId_when_getItemCatalogById_then_returnOkResponseWithCatalog")
  void given_itemId_when_getItemCatalogById_then_returnOkResponseWithCatalog() {
    // given
    final String itemId = "item123";
    final CatalogResponse mockCatalog = CatalogResponse.builder().build();
    when(catalogService.get(itemId)).thenReturn(mockCatalog);

    // when
    final ResponseEntity<CatalogResponse> result = controller.getItemCatalogById(itemId);

    // then
    assertNotNull(result);
    assertEquals(HttpStatus.OK, result.getStatusCode());
    assertEquals(mockCatalog, result.getBody());
    verify(catalogService).get(itemId);
  }

  @Test
  @DisplayName("given_differentItemId_when_getItemCatalogById_then_delegateToCatalogService")
  void given_differentItemId_when_getItemCatalogById_then_delegateToCatalogService() {
    // given
    final String itemId = "item456";
    final CatalogResponse mockCatalog = CatalogResponse.builder().build();
    when(catalogService.get(itemId)).thenReturn(mockCatalog);

    // when
    controller.getItemCatalogById(itemId);

    // then
    verify(catalogService).get(itemId);
  }
}
