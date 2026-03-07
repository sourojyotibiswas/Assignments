package com.souro.hibernate_app.dao;

import com.souro.hibernate_app.entity.Employee;

import javax.persistence.*;
import java.util.List;

public class EmployeeDao {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU01");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    // ─── Question 2: Employee CRUD ──────────────────────────────────────────────

    // Insert a new employee (with address via cascade)
    public String saveEmployee(Employee employee) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(employee);
        transaction.commit();
        return "Employee saved successfully";
    }

    // Insert multiple employees
    public String saveAllEmployee(List<Employee> employees) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        for (Employee employee : employees) {
            entityManager.persist(employee);
        }
        transaction.commit();
        return "Multiple Employees saved successfully";
    }

    // Fetch an employee by id
    public Employee getEmployeeById(int id) {
        return entityManager.find(Employee.class, id);
    }

    // Fetch all employees
    public List<Employee> getAllEmployees() {
        String jpqlQuery = "SELECT e FROM Employee e";
        TypedQuery<Employee> query = entityManager.createQuery(jpqlQuery, Employee.class);
        return query.getResultList();
    }

    // Update employee details (name, email, salary, phone)
    public String updateEmployee(int id, String newName, String newEmail, double newSalary, long newPhone) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Employee employee = entityManager.find(Employee.class, id);
        if (employee == null) {
            transaction.rollback();
            return "Employee with id " + id + " not found";
        }
        employee.setEmployeeName(newName);
        employee.setEmail(newEmail);
        employee.setSalary(newSalary);
        employee.setPhone(newPhone);
        transaction.commit();
        return "Employee updated successfully";
    }

    // Delete an employee by id (cascades to address via CascadeType.ALL + orphanRemoval)
    public String deleteEmployee(int id) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Employee employee = entityManager.find(Employee.class, id);
        if (employee == null) {
            transaction.rollback();
            return "Employee with id " + id + " not found";
        }
        entityManager.remove(employee);
        transaction.commit();
        return "Employee (and associated address) deleted successfully";
    }

    // ─── Question 3: Mapping Operations ────────────────────────────────────────

    // Save an employee along with the address in a single transaction
    public String saveEmployeeWithAddress(Employee employee) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // Because of CascadeType.ALL on Employee.address, persisting
        // the employee automatically persists the linked address.
        entityManager.persist(employee);
        transaction.commit();
        return "Employee with address saved in a single transaction";
    }

    // Fetch an employee along with the associated address
    public Employee fetchEmployeeWithAddress(int id) {
        // Address is mapped with FetchType.EAGER on the Address side,
        // so a simple find loads both entities.
        return entityManager.find(Employee.class, id);
    }

    // Update both employee salary and address city in one transaction
    public String updateSalaryAndCity(int employeeId, double newSalary, String newCity) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Employee employee = entityManager.find(Employee.class, employeeId);
        if (employee == null) {
            transaction.rollback();
            return "Employee with id " + employeeId + " not found";
        }
        employee.setSalary(newSalary);
        if (employee.getAddress() != null) {
            employee.getAddress().setCity(newCity);
        }
        transaction.commit();
        return "Salary and city updated successfully";
    }

    // Delete employee and verify cascade deletion of address
    public String deleteEmployeeAndVerifyAddressCascade(int employeeId) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Employee employee = entityManager.find(Employee.class, employeeId);
        if (employee == null) {
            transaction.rollback();
            return "Employee with id " + employeeId + " not found";
        }
        int addressId = (employee.getAddress() != null) ? employee.getAddress().getAddressId() : -1;
        entityManager.remove(employee);
        transaction.commit();

        // Verify: attempt to find the address — should return null due to CascadeType.ALL + orphanRemoval
        if (addressId != -1) {
            Object deletedAddress = entityManager.find(
                    com.souro.hibernate_app.entity.Address.class, addressId);
            if (deletedAddress == null) {
                return "Employee deleted. Address (id=" + addressId + ") was automatically deleted via cascade.";
            } else {
                return "Employee deleted but address still exists — cascade may not be configured correctly.";
            }
        }
        return "Employee deleted (no address was linked).";
    }

    // ─── Question 4: JPQL Queries ───────────────────────────────────────────────

    // 1. Fetch employees with salary greater than the given value
    public List<Employee> getEmployeesWithSalaryGreaterThan(double minSalary) {
        String jpql = "SELECT e FROM Employee e WHERE e.salary > :minSalary";
        TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
        query.setParameter("minSalary", minSalary);
        return query.getResultList();
    }

    // 2. Fetch employees with salary between a range
    public List<Employee> getEmployeesBySalaryRange(double minSalary, double maxSalary) {
        String jpql = "SELECT e FROM Employee e WHERE e.salary BETWEEN :min AND :max";
        TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
        query.setParameter("min", minSalary);
        query.setParameter("max", maxSalary);
        return query.getResultList();
    }

    // 3. Fetch employees by gender
    public List<Employee> getEmployeesByGender(String gender) {
        String jpql = "SELECT e FROM Employee e WHERE e.gender = :gender";
        TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
        query.setParameter("gender", gender);
        return query.getResultList();
    }

    // 4. Fetch employees by city (JOIN with Address)
    public List<Employee> getEmployeesByCity(String city) {
        String jpql = "SELECT e FROM Employee e JOIN e.address a WHERE a.city = :city";
        TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
        query.setParameter("city", city);
        return query.getResultList();
    }

    // 5. Fetch all employees ordered by salary (descending)
    public List<Employee> getEmployeesOrderedBySalaryDesc() {
        String jpql = "SELECT e FROM Employee e ORDER BY e.salary DESC";
        TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
        return query.getResultList();
    }

    // 6. Fetch employee names and emails (projection query)
    public List<Object[]> getEmployeeNamesAndEmails() {
        String jpql = "SELECT e.employeeName, e.email FROM Employee e";
        Query query = entityManager.createQuery(jpql);
        return query.getResultList();
    }

    // 7. Count total number of employees
    public long countEmployees() {
        String jpql = "SELECT COUNT(e) FROM Employee e";
        TypedQuery<Long> query = entityManager.createQuery(jpql, Long.class);
        return query.getSingleResult();
    }

    // 8. Fetch average salary of all employees
    public double getAverageSalary() {
        String jpql = "SELECT AVG(e.salary) FROM Employee e";
        TypedQuery<Double> query = entityManager.createQuery(jpql, Double.class);
        Double avg = query.getSingleResult();
        return (avg != null) ? avg : 0.0;
    }

    // 9. Fetch employees whose name contains a keyword (LIKE)
    public List<Employee> searchEmployeesByName(String keyword) {
        String jpql = "SELECT e FROM Employee e WHERE LOWER(e.employeeName) LIKE LOWER(CONCAT('%', :keyword, '%'))";
        TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
        query.setParameter("keyword", keyword);
        return query.getResultList();
    }

    // 10. Fetch employees from a given country (JOIN with Address)
    public List<Employee> getEmployeesByCountry(String country) {
        String jpql = "SELECT e FROM Employee e JOIN e.address a WHERE a.country = :country";
        TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
        query.setParameter("country", country);
        return query.getResultList();
    }
}
