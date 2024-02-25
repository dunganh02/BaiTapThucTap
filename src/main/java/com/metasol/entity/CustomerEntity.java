package com.metasol.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "customers")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(CustomerEntityListener.class) // Thêm đoạn này để chỉ định lớp xử lý sự kiện

public class CustomerEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "is_active")
    private boolean isActive;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private TypeEntity type;

}
