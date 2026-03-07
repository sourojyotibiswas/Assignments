package com.souro.hibernate_app.controller;

import com.souro.hibernate_app.dao.EmployeeDAO;
import com.souro.hibernate_app.entity.Employee;

import java.util.List;

public class EmployeeController {

    private final EmployeeDAO employeeDAO = new EmployeeDAO();

    public void addEmployee(Employee employee) {
        employeeDAO.insertEmployee(employee);
    }

    public void updateEmployee(String employeeCode, Employee updated) {
        employeeDAO.updateEmployee(employeeCode, updated);
    }

    public void deactivateEmployee(String employeeCode) {
        employeeDAO.softDeleteEmployee(employeeCode);
    }

    public Employee getEmployeeById(Long id) {
        return employeeDAO.findById(id);
    }

    public Employee getEmployeeByCode(String employeeCode) {
        return employeeDAO.findByEmployeeCode(employeeCode);
    }

    public List<Employee> getAllActiveEmployees() {
        return employeeDAO.findAllActive();
    }
}
