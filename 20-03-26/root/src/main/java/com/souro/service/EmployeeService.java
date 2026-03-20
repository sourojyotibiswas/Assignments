package com.souro.service;

import com.souro.dto.EmployeeDTO;
import com.souro.entity.Employee;

import java.util.List;

public interface EmployeeService {
    /// Save Employee along with Address
    /// Retrieve all Employees with their Address
    /// Retrieve Employee by ID
    /// Update Employee and Address details
    /// Delete Employee by ID

    String saveEmployee(EmployeeDTO dto);

    List<EmployeeDTO> getAllEmployees();

    Employee getEmployeeById(int id);

    String updateEmployee(int id, EmployeeDTO dto);

    String deleteEmployee(int id);
}
