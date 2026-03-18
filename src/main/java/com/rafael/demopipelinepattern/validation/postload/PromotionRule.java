package com.rafael.demopipelinepattern.validation.postload;

import com.rafael.demopipelinepattern.models.Context;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.springframework.stereotype.Component;

@Component
public class PromotionRule implements ResponseRule {

  @Override
  public boolean shouldRun(final Context context) {
    return context.getCatalogResponse() != null
        && context.getCatalogResponse().getPromotion() != null
        && context.getCatalogResponse().getItem() != null
        && context.getCatalogResponse().getItem().getPrice() != null;
  }

  @Override
  public void run(final Context context) {
    BigDecimal basePrice = context.getCatalogResponse().getItem().getPrice().getPrice();
    BigDecimal promotionPercent = context.getCatalogResponse().getPromotion().getPercent();

    BigDecimal promotionalPrice =
        basePrice.multiply(promotionPercent).divide(BigDecimal.valueOf(100), RoundingMode.CEILING);
    BigDecimal discountPrice = basePrice.subtract(promotionalPrice);

    context.getCatalogResponse().getMetadata().setPromotionalPrice(promotionalPrice);
    context.getCatalogResponse().getMetadata().setDiscountPrice(discountPrice);
  }
}
