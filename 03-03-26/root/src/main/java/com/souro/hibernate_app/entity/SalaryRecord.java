package com.souro.hibernate_app.entity;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "salary_record")
public class SalaryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // No FK mapping — treated as a plain column
    @Column(name = "employee_code", nullable = false)
    private String employeeCode;

    @Column(name = "base_salary", nullable = false)
    private BigDecimal baseSalary;

    @Column(name = "bonus")
    private BigDecimal bonus;

    @Column(name = "tax")
    private BigDecimal tax;

    @Column(name = "net_salary")
    private BigDecimal netSalary;

    // Format: YYYY-MM
    @Column(name = "salary_month", nullable = false)
    private String salaryMonth;

    public SalaryRecord() {}

    public SalaryRecord(String employeeCode, BigDecimal baseSalary,
                        BigDecimal bonus, BigDecimal tax, String salaryMonth) {
        this.employeeCode = employeeCode;
        this.baseSalary = baseSalary;
        this.bonus = bonus;
        this.tax = tax;
        this.salaryMonth = salaryMonth;
        calculateNetSalary();
    }

    public void calculateNetSalary() {
        BigDecimal b = baseSalary != null ? baseSalary : BigDecimal.ZERO;
        BigDecimal bo = bonus != null ? bonus : BigDecimal.ZERO;
        BigDecimal t = tax != null ? tax : BigDecimal.ZERO;
        this.netSalary = b.add(bo).subtract(t);
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public BigDecimal getBaseSalary() { return baseSalary; }
    public void setBaseSalary(BigDecimal baseSalary) {
        this.baseSalary = baseSalary;
        calculateNetSalary();
    }

    public BigDecimal getBonus() { return bonus; }
    public void setBonus(BigDecimal bonus) {
        this.bonus = bonus;
        calculateNetSalary();
    }

    public BigDecimal getTax() { return tax; }
    public void setTax(BigDecimal tax) {
        this.tax = tax;
        calculateNetSalary();
    }

    public BigDecimal getNetSalary() { return netSalary; }
    public void setNetSalary(BigDecimal netSalary) { this.netSalary = netSalary; }

    public String getSalaryMonth() { return salaryMonth; }
    public void setSalaryMonth(String salaryMonth) { this.salaryMonth = salaryMonth; }

    @Override
    public String toString() {
        return "SalaryRecord{id=" + id + ", employeeCode='" + employeeCode +
                "', baseSalary=" + baseSalary + ", bonus=" + bonus +
                ", tax=" + tax + ", netSalary=" + netSalary +
                ", salaryMonth='" + salaryMonth + "'}";
    }
}
