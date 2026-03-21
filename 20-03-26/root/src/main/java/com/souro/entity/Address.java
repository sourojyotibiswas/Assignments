package com.souro.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String street;

    private String city;

    private String state;

    private String country;

    private Integer pincode;


}
