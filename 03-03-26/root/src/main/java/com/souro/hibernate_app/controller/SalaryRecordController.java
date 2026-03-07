package com.souro.hibernate_app.controller;

import com.souro.hibernate_app.dao.SalaryRecordDAO;
import com.souro.hibernate_app.entity.SalaryRecord;

import java.util.List;

public class SalaryRecordController {

    private final SalaryRecordDAO salaryRecordDAO = new SalaryRecordDAO();

    public void addSalaryRecord(SalaryRecord record) {
        salaryRecordDAO.insertSalaryRecord(record);
    }

    public void updateSalaryRecord(Long id, SalaryRecord updated) {
        salaryRecordDAO.updateSalaryRecord(id, updated);
    }

    public void deleteSalaryRecord(Long id) {
        salaryRecordDAO.deleteSalaryRecord(id);
    }

    public List<SalaryRecord> getSalaryRecordsByEmployeeCode(String employeeCode) {
        return salaryRecordDAO.findByEmployeeCode(employeeCode);
    }

    public SalaryRecord getSalaryRecordByEmployeeCodeAndMonth(String employeeCode, String salaryMonth) {
        return salaryRecordDAO.findByEmployeeCodeAndMonth(employeeCode, salaryMonth);
    }

    public List<SalaryRecord> getAllSalaryRecordsForEmployee(String employeeCode) {
        return salaryRecordDAO.findAllByEmployeeCode(employeeCode);
    }
}
