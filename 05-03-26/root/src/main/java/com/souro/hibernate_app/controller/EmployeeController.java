package com.souro.hibernate_app.controller;

import com.souro.hibernate_app.dao.AddressDao;
import com.souro.hibernate_app.dao.EmployeeDao;
import com.souro.hibernate_app.entity.Address;
import com.souro.hibernate_app.entity.Employee;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmployeeController {

    public static void main(String[] args) {

        EmployeeDao employeeDao = new EmployeeDao();
        AddressDao addressDao = new AddressDao();

        // ═══════════════════════════════════════════════════════════════
        // QUESTION 2 — CRUD Operations
        // ═══════════════════════════════════════════════════════════════

        System.out.println("\n========== QUESTION 2: CRUD Operations ==========");

        // --- Insert multiple employees with addresses ---
        List<Employee> employees = getListOfEmployees();
        System.out.println(employeeDao.saveAllEmployee(employees));

        // --- Fetch all employees ---
        System.out.println("\n--- All Employees ---");
        List<Employee> allEmployees = employeeDao.getAllEmployees();
        for (Employee e : allEmployees) {
            System.out.println(e);
        }

        // Use the first employee's id for subsequent operations
        int firstId = allEmployees.get(0).getEmployeeId();

        // --- Fetch employee by id ---
        System.out.println("\n--- Fetch Employee by ID (" + firstId + ") ---");
        Employee fetched = employeeDao.getEmployeeById(firstId);
        System.out.println(fetched);

        // --- Update employee details ---
        System.out.println("\n--- Update Employee ---");
        String updateResult = employeeDao.updateEmployee(
                firstId, "Rahul Sharma Updated", "rahul.updated@example.com", 70000, 9876543210L);
        System.out.println(updateResult);
        System.out.println(employeeDao.getEmployeeById(firstId));

        // --- Address CRUD: fetch address by id ---
        int addrId = fetched.getAddress().getAddressId();
        System.out.println("\n--- Fetch Address by ID (" + addrId + ") ---");
        Address fetchedAddr = addressDao.getAddressById(addrId);
        System.out.println(fetchedAddr);

        // --- Update address ---
        System.out.println("\n--- Update Address ---");
        String addrUpdateResult = addressDao.updateAddress(
                addrId, "100 New MG Road", "Mysore", "Karnataka", "India", "570001");
        System.out.println(addrUpdateResult);
        System.out.println(addressDao.getAddressById(addrId));

        // --- Delete an employee by id (uses last employee to keep data for further demos) ---
        int lastId = allEmployees.get(allEmployees.size() - 1).getEmployeeId();
        System.out.println("\n--- Delete Employee by ID (" + lastId + ") ---");
        System.out.println(employeeDao.deleteEmployee(lastId));

        // ═══════════════════════════════════════════════════════════════
        // QUESTION 3 — Mapping Operations
        // ═══════════════════════════════════════════════════════════════

        System.out.println("\n========== QUESTION 3: Mapping Operations ==========");

        // --- Save employee along with address in a single transaction ---
        System.out.println("\n--- Save Employee with Address (single transaction) ---");
        Employee newEmp = new Employee();
        newEmp.setEmployeeName("Deepa Nair");
        newEmp.setGender("Female");
        newEmp.setEmail("deepa.nair@example.com");
        newEmp.setPassword("Deepa@999");
        newEmp.setDateOfBirth(LocalDate.of(1996, 4, 20));
        newEmp.setSalary(67000);
        newEmp.setPhone(9111222333L);

        Address newAddr = new Address();
        newAddr.setStreet("Marine Drive");
        newAddr.setCity("Kochi");
        newAddr.setState("Kerala");
        newAddr.setCountry("India");
        newAddr.setPincode("682001");
        newAddr.setEmployee(newEmp);
        newEmp.setAddress(newAddr);

        System.out.println(employeeDao.saveEmployeeWithAddress(newEmp));

        // --- Fetch employee with associated address ---
        System.out.println("\n--- Fetch Employee with Address ---");
        Employee empWithAddr = employeeDao.fetchEmployeeWithAddress(newEmp.getEmployeeId());
        System.out.println("Employee : " + empWithAddr);
        System.out.println("Address  : " + empWithAddr.getAddress());

        // --- Update both salary and city in a single transaction ---
        System.out.println("\n--- Update Salary and City Together ---");
        System.out.println(employeeDao.updateSalaryAndCity(newEmp.getEmployeeId(), 72000, "Thiruvananthapuram"));
        Employee afterUpdate = employeeDao.fetchEmployeeWithAddress(newEmp.getEmployeeId());
        System.out.println("Updated Employee : " + afterUpdate);
        System.out.println("Updated Address  : " + afterUpdate.getAddress());

        // --- Delete employee and verify address cascade deletion ---
        System.out.println("\n--- Delete Employee and Verify Address Cascade ---");
        System.out.println(employeeDao.deleteEmployeeAndVerifyAddressCascade(newEmp.getEmployeeId()));

        // ═══════════════════════════════════════════════════════════════
        // QUESTION 4 — JPQL Queries
        // ═══════════════════════════════════════════════════════════════

        System.out.println("\n========== QUESTION 4: JPQL Queries ==========");

        // 1. Fetch employees with salary > 58000
        System.out.println("\n--- 1. Employees with salary > 58000 ---");
        List<Employee> highSalary = employeeDao.getEmployeesWithSalaryGreaterThan(58000);
        highSalary.forEach(System.out::println);

        // 2. Fetch employees with salary between 55000 and 62000
        System.out.println("\n--- 2. Employees with salary between 55000 and 62000 ---");
        List<Employee> salaryRange = employeeDao.getEmployeesBySalaryRange(55000, 62000);
        salaryRange.forEach(System.out::println);

        // 3. Fetch employees by gender
        System.out.println("\n--- 3. Female Employees ---");
        List<Employee> females = employeeDao.getEmployeesByGender("Female");
        females.forEach(System.out::println);

        // 4. Fetch employees by city
        System.out.println("\n--- 4. Employees in Kolkata ---");
        List<Employee> inKolkata = employeeDao.getEmployeesByCity("Kolkata");
        inKolkata.forEach(System.out::println);

        // 5. Fetch all employees ordered by salary (descending)
        System.out.println("\n--- 5. Employees ordered by salary (DESC) ---");
        List<Employee> ordered = employeeDao.getEmployeesOrderedBySalaryDesc();
        ordered.forEach(System.out::println);

        // 6. Projection: employee names and emails
        System.out.println("\n--- 6. Employee Names and Emails ---");
        List<Object[]> namesEmails = employeeDao.getEmployeeNamesAndEmails();
        for (Object[] row : namesEmails) {
            System.out.println("Name: " + row[0] + " | Email: " + row[1]);
        }

        // 7. Count total employees
        System.out.println("\n--- 7. Total Employee Count ---");
        System.out.println("Count: " + employeeDao.countEmployees());

        // 8. Average salary
        System.out.println("\n--- 8. Average Salary ---");
        System.out.println("Average Salary: " + employeeDao.getAverageSalary());

        // 9. Search by name keyword
        System.out.println("\n--- 9. Employees whose name contains 'kumar' ---");
        List<Employee> searched = employeeDao.searchEmployeesByName("kumar");
        searched.forEach(System.out::println);

        // 10. Employees by country
        System.out.println("\n--- 10. Employees from India ---");
        List<Employee> fromIndia = employeeDao.getEmployeesByCountry("India");
        fromIndia.forEach(System.out::println);
    }

    // ─── Seed data ──────────────────────────────────────────────────────────────

    public static List<Employee> getListOfEmployees() {
        List<Employee> employees = new ArrayList<>();

        // Employee 1
        Employee e1 = new Employee();
        e1.setEmployeeName("Rahul Sharma");
        e1.setGender("Male");
        e1.setEmail("rahul.sharma@example.com");
        e1.setPassword("Rahul@123");
        e1.setDateOfBirth(LocalDate.of(1995, 5, 15));
        e1.setSalary(55000);
        e1.setPhone(9876543210L);

        Address a1 = new Address();
        a1.setStreet("12 MG Road");
        a1.setPincode("560001");
        a1.setCity("Bangalore");
        a1.setState("Karnataka");
        a1.setCountry("India");
        a1.setEmployee(e1);
        e1.setAddress(a1);
        employees.add(e1);

        // Employee 2
        Employee e2 = new Employee();
        e2.setEmployeeName("Priya Verma");
        e2.setGender("Female");
        e2.setEmail("priya.verma@example.com");
        e2.setPassword("Priya@456");
        e2.setDateOfBirth(LocalDate.of(1998, 9, 22));
        e2.setSalary(62000);
        e2.setPhone(9123456780L);

        Address a2 = new Address();
        a2.setStreet("45 Park Street");
        a2.setPincode("700016");
        a2.setCity("Kolkata");
        a2.setState("West Bengal");
        a2.setCountry("India");
        a2.setEmployee(e2);
        e2.setAddress(a2);
        employees.add(e2);

        // Employee 3
        Employee e3 = new Employee();
        e3.setEmployeeName("Amit Kumar");
        e3.setGender("Male");
        e3.setEmail("amit.kumar@example.com");
        e3.setPassword("Amit@789");
        e3.setDateOfBirth(LocalDate.of(1993, 3, 10));
        e3.setSalary(58000);
        e3.setPhone(9012345678L);

        Address a3 = new Address();
        a3.setStreet("Sector 18");
        a3.setPincode("201301");
        a3.setCity("Noida");
        a3.setState("Uttar Pradesh");
        a3.setCountry("India");
        a3.setEmployee(e3);
        e3.setAddress(a3);
        employees.add(e3);

        // Employee 4
        Employee e4 = new Employee();
        e4.setEmployeeName("Sneha Reddy");
        e4.setGender("Female");
        e4.setEmail("sneha.reddy@example.com");
        e4.setPassword("Sneha@321");
        e4.setDateOfBirth(LocalDate.of(1997, 12, 5));
        e4.setSalary(61000);
        e4.setPhone(9988776655L);

        Address a4 = new Address();
        a4.setStreet("Banjara Hills");
        a4.setPincode("500034");
        a4.setCity("Hyderabad");
        a4.setState("Telangana");
        a4.setCountry("India");
        a4.setEmployee(e4);
        e4.setAddress(a4);
        employees.add(e4);

        // Employee 5
        Employee e5 = new Employee();
        e5.setEmployeeName("Arjun Patel");
        e5.setGender("Male");
        e5.setEmail("arjun.patel@example.com");
        e5.setPassword("Arjun@654");
        e5.setDateOfBirth(LocalDate.of(1994, 7, 18));
        e5.setSalary(59000);
        e5.setPhone(9090909090L);

        Address a5 = new Address();
        a5.setStreet("SG Highway");
        a5.setPincode("380015");
        a5.setCity("Ahmedabad");
        a5.setState("Gujarat");
        a5.setCountry("India");
        a5.setEmployee(e5);
        e5.setAddress(a5);
        employees.add(e5);

        return employees;
    }
}
