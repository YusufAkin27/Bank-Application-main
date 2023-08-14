package com.example.finalproject.customer.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Size(min = 3, max = 15)
    private String country;

    @Size(min = 3, max = 15)
    private String city;

    @Size(min = 3, max = 15)
    private String district;

    @Size(min = 3, max = 50)
    private String streetNumber;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JsonBackReference
    private Customer customer;
}
