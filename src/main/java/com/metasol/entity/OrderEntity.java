package com.metasol.entity;

import jakarta.persistence.*;
import lombok.*;

import java.security.PrivateKey;

@Entity
@Table(name = "orders")
@Data
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "quantity_sold")
    private Long quantitySold;

    @Column(name = "total_money")
    private float totalMoney;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    private CustomerEntity customer;


}
