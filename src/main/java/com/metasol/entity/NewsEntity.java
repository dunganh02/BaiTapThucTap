package com.metasol.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "news")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String image;


}
