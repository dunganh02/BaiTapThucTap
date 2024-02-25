package com.metasol.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.metasol.entity.TypeEntity;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CustomerRequestDto {

    private String name;

    @JsonProperty("phone_number")
    private String phoneNumber;

    @JsonProperty("is_active")
    private boolean isActive;

    @JsonProperty("type_id")
    private TypeEntity typeId;
}
