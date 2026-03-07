package com.souro.hibernate_app.controller;

import com.souro.hibernate_app.dao.PerformanceReviewDAO;
import com.souro.hibernate_app.entity.PerformanceReview;

import java.time.LocalDate;
import java.util.List;

public class PerformanceReviewController {

    private final PerformanceReviewDAO performanceReviewDAO = new PerformanceReviewDAO();

    public void addReview(PerformanceReview review) {
        performanceReviewDAO.insertReview(review);
    }

    public void updateReview(Long id, PerformanceReview updated) {
        performanceReviewDAO.updateReview(id, updated);
    }

    public void deleteReview(Long id) {
        performanceReviewDAO.deleteReview(id);
    }

    public List<PerformanceReview> getReviewsByEmployeeCode(String employeeCode) {
        return performanceReviewDAO.findByEmployeeCode(employeeCode);
    }

    public List<PerformanceReview> getReviewsByDateRange(LocalDate from, LocalDate to) {
        return performanceReviewDAO.findByDateRange(from, to);
    }

    public List<PerformanceReview> getAllReviews() {
        return performanceReviewDAO.findAll();
    }
}
