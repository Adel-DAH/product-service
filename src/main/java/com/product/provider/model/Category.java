package com.product.provider.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Category {

    @Id
    @GeneratedValue
    private Long id;
    @Column(name="name")
    private String name;
    @OneToMany
    private List<Product> products;
}
