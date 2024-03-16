package com.metasol.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductImageResponseDto {
    @JsonProperty("product_id")
    private Long productId;

    @JsonProperty("image_url")
    private List<String> imageUrl;
}
