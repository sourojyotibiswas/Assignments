package com.souro.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Table(name = "employee")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private String employeeName;

    private String email;

    private String phone;

    private String gender;

    private String designation;

    private double salary;

    private String dateOfJoining;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id")
    private Address address;
}
