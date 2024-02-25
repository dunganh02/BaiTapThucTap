package com.metasol.dto.request;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NewsRequestDto {
    private String title;

    private String content;

    private String image;
}
