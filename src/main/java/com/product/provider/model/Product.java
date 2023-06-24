package com.product.provider.model;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Entity
public class Product {

    @Id
    @GeneratedValue
    private Long  id;
    @Column(name = "reference",unique = true)
    private String reference;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToOne
    private Category category;

}