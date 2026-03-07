package com.souro.hibernate_app.dao;

import com.souro.hibernate_app.entity.PerformanceReview;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.time.LocalDate;
import java.util.List;

public class PerformanceReviewDAO {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public void insertReview(PerformanceReview review) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(review);
            em.getTransaction().commit();
            System.out.println("PerformanceReview inserted: " + review);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void updateReview(Long id, PerformanceReview updated) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            PerformanceReview existing = em.find(PerformanceReview.class, id);
            if (existing == null) {
                throw new RuntimeException("PerformanceReview not found with id: " + id);
            }
            existing.setEmployeeCode(updated.getEmployeeCode());
            existing.setRating(updated.getRating());
            existing.setReviewDate(updated.getReviewDate());
            existing.setComments(updated.getComments());
            em.getTransaction().commit();
            System.out.println("PerformanceReview updated: " + existing);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteReview(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            PerformanceReview existing = em.find(PerformanceReview.class, id);
            if (existing == null) {
                throw new RuntimeException("PerformanceReview not found with id: " + id);
            }
            em.remove(existing);
            em.getTransaction().commit();
            System.out.println("PerformanceReview deleted with id: " + id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<PerformanceReview> findByEmployeeCode(String employeeCode) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<PerformanceReview> query = em.createQuery(
                    "SELECT r FROM PerformanceReview r WHERE r.employeeCode = :code",
                    PerformanceReview.class);
            query.setParameter("code", employeeCode);
            List<PerformanceReview> list = query.getResultList();
            System.out.println("Reviews for " + employeeCode + ": " + list.size());
            return list;
        } finally {
            em.close();
        }
    }

    public List<PerformanceReview> findByDateRange(LocalDate from, LocalDate to) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<PerformanceReview> query = em.createQuery(
                    "SELECT r FROM PerformanceReview r WHERE r.reviewDate BETWEEN :from AND :to",
                    PerformanceReview.class);
            query.setParameter("from", from);
            query.setParameter("to", to);
            List<PerformanceReview> list = query.getResultList();
            System.out.println("Reviews between " + from + " and " + to + ": " + list.size());
            return list;
        } finally {
            em.close();
        }
    }

    public List<PerformanceReview> findAll() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<PerformanceReview> query = em.createQuery(
                    "SELECT r FROM PerformanceReview r", PerformanceReview.class);
            List<PerformanceReview> list = query.getResultList();
            System.out.println("Total reviews: " + list.size());
            return list;
        } finally {
            em.close();
        }
    }
}
