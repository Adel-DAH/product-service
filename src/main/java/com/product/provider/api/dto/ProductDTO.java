package com.product.provider.api.dto;

import com.product.provider.model.Category;
import jakarta.annotation.Nonnull;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {


    private Long  id;
    @NotNull
    @NotBlank
    private String reference;
    private String name;
    private String description;
    private BigDecimal price;
    @NotNull
    private CategoryDTO category;
}
