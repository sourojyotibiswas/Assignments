package com.souro.hibernate_app.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "address_id", nullable = false, unique = true)
    private int addressId;
    @Column(name = "street")
    private String street;
    @Column(name = "pincode")
    private String pincode;
    @Column(name = "city")
    private String city;
    @Column(name = "state")
    private String state;
    @Column(name = "country")
    private String country;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Address() {}

    public Address(int addressId, String street, String pincode, String city, String state, String country, Employee employee) {
        this.addressId = addressId;
        this.street = street;
        this.pincode = pincode;
        this.city = city;
        this.state = state;
        this.country = country;
        this.employee = employee;
    }

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "Address{" +
                "employee=" + employee +
                ", street='" + street + '\'' +
                ", addressId=" + addressId +
                ", city='" + city + '\'' +
                ", pincode='" + pincode + '\'' +
                ", state='" + state + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return addressId == address.addressId && Objects.equals(street, address.street) && Objects.equals(pincode, address.pincode) && Objects.equals(city, address.city) && Objects.equals(state, address.state) && Objects.equals(country, address.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressId, street, pincode, city, state, country);
    }
}