package com.example.springbootthymeleaftw.model.entity;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name="product", schema = "public", catalog = "tw")
public class ProductEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "description")
    private String description;

    @Basic
    @Column(name = "stock")
    private int stock;

    @Basic
    @Column(name = "approved")
    private String approved;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "business_id", referencedColumnName = "id")
    private BusinessEntity business;
}
