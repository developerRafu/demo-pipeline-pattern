package com.rafael.demopipelinepattern.models.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetaDataResponse {
    private BigDecimal promotionalPrice;
    private BigDecimal discountPrice;
    private boolean isAvailable;
}
