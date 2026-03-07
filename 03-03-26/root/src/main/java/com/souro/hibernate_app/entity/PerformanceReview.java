package com.souro.hibernate_app.entity;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "performance_review")
public class PerformanceReview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // No FK mapping — treated as a plain column
    @Column(name = "employee_code", nullable = false)
    private String employeeCode;

    // Rating: 1 to 5
    @Column(name = "rating", nullable = false)
    private int rating;

    @Column(name = "review_date", nullable = false)
    private LocalDate reviewDate;

    @Column(name = "comments")
    private String comments;

    public PerformanceReview() {}

    public PerformanceReview(String employeeCode, int rating,
                             LocalDate reviewDate, String comments) {
        this.employeeCode = employeeCode;
        this.rating = rating;
        this.reviewDate = reviewDate;
        this.comments = comments;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEmployeeCode() { return employeeCode; }
    public void setEmployeeCode(String employeeCode) { this.employeeCode = employeeCode; }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public LocalDate getReviewDate() { return reviewDate; }
    public void setReviewDate(LocalDate reviewDate) { this.reviewDate = reviewDate; }

    public String getComments() { return comments; }
    public void setComments(String comments) { this.comments = comments; }

    @Override
    public String toString() {
        return "PerformanceReview{id=" + id + ", employeeCode='" + employeeCode +
                "', rating=" + rating + ", reviewDate=" + reviewDate +
                ", comments='" + comments + "'}";
    }
}
