package com.metasol.dto.response;

import com.metasol.entity.TypeEntity;
import jakarta.persistence.JoinColumn;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerResponseDto {

    private String name;

    @JoinColumn(name = "phone_number")
    private String phoneNumber;

    @JoinColumn(name = "is_active")
    private boolean isActive;

    @JoinColumn(name = "type_id")
    private TypeEntity type;

}
