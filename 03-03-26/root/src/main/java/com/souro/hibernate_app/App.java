package com.souro.hibernate_app;

import com.souro.hibernate_app.controller.EmployeeController;
import com.souro.hibernate_app.controller.PerformanceReviewController;
import com.souro.hibernate_app.controller.SalaryRecordController;
import com.souro.hibernate_app.dao.JPAUtil;
import com.souro.hibernate_app.entity.Employee;
import com.souro.hibernate_app.entity.PerformanceReview;
import com.souro.hibernate_app.entity.SalaryRecord;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class App {

    public static void main(String[] args) {
        EmployeeController employeeController = new EmployeeController();
        SalaryRecordController salaryController = new SalaryRecordController();
        PerformanceReviewController reviewController = new PerformanceReviewController();

        // ── Question 1: Employee Management ──────────────────────────────────

        // Insert employees
        Employee emp1 = new Employee("EMP001", "Alice Johnson", "alice@example.com",
                "Software Engineer", "Engineering", LocalDate.of(2022, 3, 15));
        Employee emp2 = new Employee("EMP002", "Bob Smith", "bob@example.com",
                "QA Engineer", "Quality", LocalDate.of(2021, 7, 1));
        employeeController.addEmployee(emp1);
        employeeController.addEmployee(emp2);

        // Update employee details
        Employee updatedEmp = new Employee();
        updatedEmp.setName("Alice Johnson-Williams");
        updatedEmp.setEmail("alice.williams@example.com");
        updatedEmp.setDesignation("Senior Software Engineer");
        updatedEmp.setDepartment("Engineering");
        updatedEmp.setJoiningDate(LocalDate.of(2022, 3, 15));
        employeeController.updateEmployee("EMP001", updatedEmp);

        // Fetch by id and by employeeCode
        Employee fetchedById = employeeController.getEmployeeById(1L);
        System.out.println("Fetched by ID: " + fetchedById);

        Employee fetchedByCode = employeeController.getEmployeeByCode("EMP002");
        System.out.println("Fetched by Code: " + fetchedByCode);

        // Fetch all active employees (JPQL)
        List<Employee> activeEmployees = employeeController.getAllActiveEmployees();
        System.out.println("All active employees: " + activeEmployees);

        // Soft delete employee
        employeeController.deactivateEmployee("EMP002");

        // Verify soft delete — EMP002 should no longer appear in active list
        activeEmployees = employeeController.getAllActiveEmployees();
        System.out.println("Active employees after soft-delete: " + activeEmployees);

        // ── Question 2: Salary Record Management ─────────────────────────────

        // Insert salary records (netSalary auto-calculated: base + bonus - tax)
        SalaryRecord sal1 = new SalaryRecord("EMP001",
                new BigDecimal("80000"), new BigDecimal("5000"), new BigDecimal("12000"),
                "2025-01");
        SalaryRecord sal2 = new SalaryRecord("EMP001",
                new BigDecimal("80000"), new BigDecimal("3000"), new BigDecimal("12000"),
                "2025-02");
        salaryController.addSalaryRecord(sal1);
        salaryController.addSalaryRecord(sal2);

        // Fetch by employeeCode
        List<SalaryRecord> salaries = salaryController.getSalaryRecordsByEmployeeCode("EMP001");
        System.out.println("Salary records for EMP001: " + salaries);

        // Fetch by employeeCode and salaryMonth
        SalaryRecord salByMonth = salaryController.getSalaryRecordByEmployeeCodeAndMonth("EMP001", "2025-01");
        System.out.println("Salary for EMP001 in 2025-01: " + salByMonth);

        // Update salary record
        SalaryRecord updatedSal = new SalaryRecord("EMP001",
                new BigDecimal("85000"), new BigDecimal("7000"), new BigDecimal("13000"),
                "2025-01");
        if (salByMonth != null) {
            salaryController.updateSalaryRecord(salByMonth.getId(), updatedSal);
        }

        // Fetch all salary records for employee
        List<SalaryRecord> allSalaries = salaryController.getAllSalaryRecordsForEmployee("EMP001");
        System.out.println("All salary records for EMP001: " + allSalaries);

        // Delete a salary record
        if (!allSalaries.isEmpty()) {
            salaryController.deleteSalaryRecord(allSalaries.get(0).getId());
        }

        // ── Question 3: Performance Review Management ─────────────────────────

        // Insert performance reviews
        PerformanceReview rev1 = new PerformanceReview("EMP001", 5,
                LocalDate.of(2025, 1, 10), "Outstanding performance this quarter.");
        PerformanceReview rev2 = new PerformanceReview("EMP001", 4,
                LocalDate.of(2024, 7, 15), "Good progress, needs leadership development.");
        reviewController.addReview(rev1);
        reviewController.addReview(rev2);

        // Fetch by employeeCode
        List<PerformanceReview> reviews = reviewController.getReviewsByEmployeeCode("EMP001");
        System.out.println("Reviews for EMP001: " + reviews);

        // Fetch by date range
        List<PerformanceReview> reviewsInRange = reviewController.getReviewsByDateRange(
                LocalDate.of(2024, 1, 1), LocalDate.of(2025, 12, 31));
        System.out.println("Reviews in 2024-2025: " + reviewsInRange);

        // Fetch all reviews
        List<PerformanceReview> allReviews = reviewController.getAllReviews();
        System.out.println("All reviews: " + allReviews);

        // Update a review
        if (!reviews.isEmpty()) {
            PerformanceReview updatedReview = new PerformanceReview("EMP001", 5,
                    LocalDate.of(2025, 1, 10), "Exceptional — promoted to Tech Lead.");
            reviewController.updateReview(reviews.get(0).getId(), updatedReview);
        }

        // Delete a review
        if (reviews.size() > 1) {
            reviewController.deleteReview(reviews.get(1).getId());
        }

        JPAUtil.close();
        System.out.println("All operations completed successfully.");
    }
}
