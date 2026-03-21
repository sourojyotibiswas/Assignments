package com.souro.service;

import com.souro.dto.AddressDTO;
import com.souro.dto.EmployeeDTO;
import com.souro.entity.Address;
import com.souro.entity.Employee;
import com.souro.exception.EmployeeNotFoundException;
import com.souro.repository.EmployeeRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public String saveEmployee(EmployeeDTO dto) {

        Employee employee = Employee.builder()
                .employeeName(dto.getEmployeeName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .designation(dto.getDesignation())
                .salary(dto.getSalary())
                .dateOfJoining(dto.getDateOfJoining())
                .address(
                        Address.builder()
                                .street(dto.getAddress().getStreet())
                                .city(dto.getAddress().getCity())
                                .state(dto.getAddress().getState())
                                .country(dto.getAddress().getCountry())
                                .pincode(dto.getAddress().getPincode())
                                .build()
                ).build();
        int employeeId = employeeRepository.save(employee).getId();
        return "Employee of id " + employeeId + " saved successfully";
    }

    @Override
    public List<EmployeeDTO> getAllEmployees() {

        List<Employee> employees = employeeRepository.findAll();
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();

        for (Employee employee : employees) {

            Address address = employee.getAddress();

            AddressDTO addressDTO = null;

            if (address != null) {
                addressDTO = AddressDTO.builder()
                        .street(address.getStreet())
                        .city(address.getCity())
                        .state(address.getState())
                        .country(address.getCountry())
                        .pincode(address.getPincode())
                        .build();
            }

            EmployeeDTO dto = EmployeeDTO.builder()
                    .employeeName(employee.getEmployeeName())
                    .email(employee.getEmail())
                    .phone(employee.getPhone())
                    .gender(employee.getGender())
                    .designation(employee.getDesignation())
                    .salary(employee.getSalary())
                    .dateOfJoining(employee.getDateOfJoining())
                    .address(addressDTO)
                    .build();

            employeeDTOList.add(dto);
        }

        return employeeDTOList;
    }

    @Override
    public Employee getEmployeeById(int id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException(id));
    }

    @Override
    public String updateEmployee(int id, EmployeeDTO dto) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(id);

        Employee employee = optionalEmployee.orElseThrow(() -> new EmployeeNotFoundException(id));

        if (dto.getEmployeeName() != null) {
            employee.setEmployeeName(dto.getEmployeeName());
        }
        if (dto.getEmail() != null) {
            employee.setEmail(dto.getEmail());
        }
        if (dto.getPhone() != null) {
            employee.setPhone(dto.getPhone());
        }
        if (dto.getGender() != null) {
            employee.setGender(dto.getGender());
        }
        if (dto.getDesignation() != null) {
            employee.setDesignation(dto.getDesignation());
        }
        if (dto.getSalary() > 0) {
            employee.setSalary(dto.getSalary());
        }
        if (dto.getDateOfJoining() != null) {
            employee.setDateOfJoining(dto.getDateOfJoining());
        }
        if (dto.getAddress() != null) {
            Address address = employee.getAddress();

            if (address == null) {
                address = new Address();
            }
            if (dto.getAddress().getStreet() != null) {
                address.setStreet(dto.getAddress().getStreet());
            }
            if (dto.getAddress().getCity() != null) {
                address.setCity(dto.getAddress().getCity());
            }
            if (dto.getAddress().getState() != null) {
                address.setState(dto.getAddress().getState());
            }
            if (dto.getAddress().getCountry() != null) {
                address.setCountry(dto.getAddress().getCountry());
            }
            if (dto.getAddress().getPincode() != 0) {
                address.setPincode(dto.getAddress().getPincode());
            }

            employee.setAddress(address);
        }

        int employeeId = employeeRepository.save(employee).getId();
        return "Employee of id " + employeeId + " updated successfully";
    }

    @Override
    public String deleteEmployee(int id) {
        if (!employeeRepository.existsById(id)) {
            throw new EmployeeNotFoundException(id);
        }
        employeeRepository.deleteById(id);
        return "Employee deleted successfully";
    }
}
