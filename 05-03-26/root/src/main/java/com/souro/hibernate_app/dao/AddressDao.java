package com.souro.hibernate_app.dao;

import com.souro.hibernate_app.entity.Address;
import com.souro.hibernate_app.entity.Employee;

import javax.persistence.*;

public class AddressDao {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PU01");
    EntityManager entityManager = entityManagerFactory.createEntityManager();

    // ─── Question 2: Address CRUD ───────────────────────────────────────────────

    // Insert an address for an existing employee
    public String saveAddress(Address address) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(address);
        transaction.commit();
        return "Address saved successfully";
    }

    // Fetch an address by id
    public Address getAddressById(int id) {
        return entityManager.find(Address.class, id);
    }

    // Update address details (street, city, state, country, pincode)
    public String updateAddress(int addressId, String newStreet, String newCity,
                                String newState, String newCountry, String newPincode) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Address address = entityManager.find(Address.class, addressId);
        if (address == null) {
            transaction.rollback();
            return "Address with id " + addressId + " not found";
        }
        address.setStreet(newStreet);
        address.setCity(newCity);
        address.setState(newState);
        address.setCountry(newCountry);
        address.setPincode(newPincode);
        transaction.commit();
        return "Address updated successfully";
    }

    // Delete an address by id
    public String deleteAddress(int addressId) {
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        Address address = entityManager.find(Address.class, addressId);
        if (address == null) {
            transaction.rollback();
            return "Address with id " + addressId + " not found";
        }
        // Unlink from employee so the employee row is not also deleted
        Employee employee = address.getEmployee();
        if (employee != null) {
            employee.setAddress(null);
        }
        entityManager.remove(address);
        transaction.commit();
        return "Address deleted successfully";
    }
}
