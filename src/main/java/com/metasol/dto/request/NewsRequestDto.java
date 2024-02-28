package com.metasol.dto.request;

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
