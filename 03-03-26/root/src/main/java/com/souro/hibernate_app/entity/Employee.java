package com.souro.hibernate_app.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "employee_code", nullable = false, unique = true)
    private String employeeCode;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "designation")
    private String designation;

    @Column(name = "department")
    private String department;

    @Column(name = "joining_date")
    private LocalDate joiningDate;

    @Column(name = "active")
    private boolean active = true;

    public Employee() {}

    public Employee(String employeeCode, String name, String email,
                    String designation, String department, LocalDate joiningDate) {
        this.employeeCode = employeeCode;
        this.name = name;
        this.email = email;
        this.designation = designation;
        this.department = department;
        this.joiningDate = joiningDate;
        this.active = true;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getDesignation() { return designation; }
    public void setDesignation(String designation) { this.designation = designation; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public LocalDate getJoiningDate() { return joiningDate; }
    public void setJoiningDate(LocalDate joiningDate) { this.joiningDate = joiningDate; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    @Override
    public String toString() {
        return "Employee{id=" + id + ", employeeCode='" + employeeCode + "', name='" + name +
                "', email='" + email + "', designation='" + designation +
                "', department='" + department + "', joiningDate=" + joiningDate +
                ", active=" + active + "}";
    }
}
