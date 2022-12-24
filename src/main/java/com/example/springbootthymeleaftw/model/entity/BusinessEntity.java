package com.example.springbootthymeleaftw.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name="business", schema = "public", catalog = "tw")
public class BusinessEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic
    @Column(name = "company_name")
    private String companyName;

    @Basic
    @Column(name = "address")
    private String address;

    @Basic
    @Column(name = "id_code")
    private String idCode;

    @Basic
    @Column(name = "business_type")
    private String businessType;

    @Basic
    @Column(name = "approved")
    private boolean approved;

    @ManyToMany(mappedBy = "business")
    @JsonIgnore
    private Collection<UserEntity> users;
}