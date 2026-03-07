package com.souro.hibernate_app.dao;

import com.souro.hibernate_app.entity.SalaryRecord;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import java.math.BigDecimal;
import java.util.List;

public class SalaryRecordDAO {

    private final EntityManagerFactory emf = JPAUtil.getEntityManagerFactory();

    public void insertSalaryRecord(SalaryRecord record) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            record.calculateNetSalary();
            em.persist(record);
            em.getTransaction().commit();
            System.out.println("SalaryRecord inserted: " + record);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void updateSalaryRecord(Long id, SalaryRecord updated) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SalaryRecord existing = em.find(SalaryRecord.class, id);
            if (existing == null) {
                throw new RuntimeException("SalaryRecord not found with id: " + id);
            }
            existing.setEmployeeCode(updated.getEmployeeCode());
            existing.setBaseSalary(updated.getBaseSalary());
            existing.setBonus(updated.getBonus());
            existing.setTax(updated.getTax());
            existing.setSalaryMonth(updated.getSalaryMonth());
            existing.calculateNetSalary();
            em.getTransaction().commit();
            System.out.println("SalaryRecord updated: " + existing);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public void deleteSalaryRecord(Long id) {
        EntityManager em = emf.createEntityManager();
        try {
            em.getTransaction().begin();
            SalaryRecord existing = em.find(SalaryRecord.class, id);
            if (existing == null) {
                throw new RuntimeException("SalaryRecord not found with id: " + id);
            }
            em.remove(existing);
            em.getTransaction().commit();
            System.out.println("SalaryRecord deleted with id: " + id);
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    public List<SalaryRecord> findByEmployeeCode(String employeeCode) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<SalaryRecord> query = em.createQuery(
                    "SELECT s FROM SalaryRecord s WHERE s.employeeCode = :code",
                    SalaryRecord.class);
            query.setParameter("code", employeeCode);
            List<SalaryRecord> list = query.getResultList();
            System.out.println("SalaryRecords for " + employeeCode + ": " + list.size());
            return list;
        } finally {
            em.close();
        }
    }

    public SalaryRecord findByEmployeeCodeAndMonth(String employeeCode, String salaryMonth) {
        EntityManager em = emf.createEntityManager();
        try {
            TypedQuery<SalaryRecord> query = em.createQuery(
                    "SELECT s FROM SalaryRecord s WHERE s.employeeCode = :code AND s.salaryMonth = :month",
                    SalaryRecord.class);
            query.setParameter("code", employeeCode);
            query.setParameter("month", salaryMonth);
            List<SalaryRecord> results = query.getResultList();
            SalaryRecord record = results.isEmpty() ? null : results.get(0);
            System.out.println("SalaryRecord for " + employeeCode + " / " + salaryMonth + ": " + record);
            return record;
        } finally {
            em.close();
        }
    }

    public List<SalaryRecord> findAllByEmployeeCode(String employeeCode) {
        return findByEmployeeCode(employeeCode);
    }
}
