package com.metasol.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.core.annotation.Order;

@Entity
@Table(name = "order_details")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "number_of_product")
    private int numberOfProduct;

    @Column
    private float price;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private OrderEntity order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductEntity product;

}
