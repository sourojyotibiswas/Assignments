package com.souro.hibernate_app.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "employee")
public class Employee {
    @Id // primary key
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "employee_id", nullable = false, unique = true) // used to modify the name inside table and add constraint
    private int employeeId;
    @Column(name = "employee_name")
    private String employeeName;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;
    @Column(name = "gender")
    private String gender;
    @Column(name = "salary")
    private double salary;
    @Column(name = "phone")
    private long phone;
//    @JoinColumn(name = "employee_name")
    @OneToOne(mappedBy = "employee", cascade = CascadeType.ALL, orphanRemoval = true) // to establish the relationship b/w two table
    private Address address;

    public Employee() {}

    public Employee(int employeeId, String employeeName, String email, String password, LocalDate dateOfBirth, String gender, double salary, long phone, Address address) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.email = email;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.salary = salary;
        this.phone = phone;
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public long getPhone() {
        return phone;
    }

    public void setPhone(long phone) {
        this.phone = phone;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return employeeId == employee.employeeId && Double.compare(salary, employee.salary) == 0 && phone == employee.phone && Objects.equals(employeeName, employee.employeeName) && Objects.equals(email, employee.email) && Objects.equals(password, employee.password) && Objects.equals(dateOfBirth, employee.dateOfBirth) && Objects.equals(gender, employee.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId, employeeName, email, password, dateOfBirth, gender, salary, phone);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender='" + gender + '\'' +
                ", salary=" + salary +
                ", phone=" + phone +
                '}';
    }
}
