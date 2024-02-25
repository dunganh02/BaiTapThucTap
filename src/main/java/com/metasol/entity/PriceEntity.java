package com.metasol.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prices")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PriceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeEntity type;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

    @Column(name = "price")
    private float price;
}
