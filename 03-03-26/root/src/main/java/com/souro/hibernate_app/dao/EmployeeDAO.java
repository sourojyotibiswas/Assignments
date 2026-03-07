package com.souro.hibernate_app.dao;

import com.souro.hibernate_app.entity.Employee;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.util.List;

public class EmployeeDAO {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public void insertEmployee(Employee employee) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(employee);
            em.getTransaction().commit();
            System.out.println("Employee inserted: " + employee);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void updateEmployee(String employeeCode, Employee updated) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Employee existing = findByEmployeeCode(em, employeeCode);
            if (existing == null) {
                throw new RuntimeException("Employee not found with code: " + employeeCode);
            }
            existing.setName(updated.getName());
            existing.setEmail(updated.getEmail());
            existing.setDesignation(updated.getDesignation());
            existing.setDepartment(updated.getDepartment());
            existing.setJoiningDate(updated.getJoiningDate());
            em.getTransaction().commit();
            System.out.println("Employee updated: " + existing);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void softDeleteEmployee(String employeeCode) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Employee existing = findByEmployeeCode(em, employeeCode);
            if (existing == null) {
                throw new RuntimeException("Employee not found with code: " + employeeCode);
            }
            existing.setActive(false);
            em.getTransaction().commit();
            System.out.println("Employee soft-deleted (active=false): " + employeeCode);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public Employee findById(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            Employee employee = em.find(Employee.class, id);
            System.out.println("Found by id: " + employee);
            return employee;
        } finally {
            em.close();
        }
    }

    public Employee findByEmployeeCode(String employeeCode) {
        EntityManager em = emf.createEntityManager();
        try {
            Employee employee = findByEmployeeCode(em, employeeCode);
            System.out.println("Found by employeeCode: " + employee);
            return employee;
        } finally {
            em.close();
        }
    }

    public List<Employee> findAllActive() {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<Employee> query = em.createQuery(
                    "SELECT e FROM Employee e WHERE e.active = true", Employee.class);
            List<Employee> list = query.getResultList();
            System.out.println("Active employees count: " + list.size());
            return list;
        } finally {
            em.close();
        }
    }

    // Private helper to avoid opening a second EntityManager inside a transaction
    private Employee findByEmployeeCode(EntityManager em, String employeeCode) {
        TypedQuery<Employee> query = em.createQuery(
                "SELECT e FROM Employee e WHERE e.employeeCode = :code", Employee.class);
        query.setParameter("code", employeeCode);
        List<Employee> results = query.getResultList();
        return results.isEmpty() ? null : results.get(0);
    }
}
