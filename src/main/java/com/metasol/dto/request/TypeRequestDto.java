package com.metasol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TypeRequestDto {

    @JsonProperty("type_name")
    private String typeName;
}
