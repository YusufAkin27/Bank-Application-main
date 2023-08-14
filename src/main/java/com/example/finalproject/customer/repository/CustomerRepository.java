package com.example.finalproject.customer.repository;



import com.example.finalproject.customer.core.exception.*;
import com.example.finalproject.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    @Query("SELECT c FROM Customer c WHERE LOWER(CONCAT(c.id, '/', c.identityNumber, '/', c.telephone, '/', c.email, '/', c.birthDay, '/', c.createdAt, '/', c.name, '/', c.surname, '/', c.income)) LIKE %?1%")
    List<Customer> search(String customerInfo);
    default Customer findByCustomerId(long customerId) throws CustomerNotFoundException {
        return findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException());
    }

    @Query("SELECT c FROM Customer c WHERE LOWER(c.identityNumber) = LOWER(?1)")
    Optional<Customer> getByIdentityNumber(String identityNumber) throws CustomerNotFoundException;


    @Query("SELECT c FROM Customer c WHERE c.identityNumber = :identityNumber")
    Optional<Customer> findByIdentityNumber(String identityNumber);

    @Query("SELECT c FROM Customer c WHERE c.telephone = :telephone")
    Optional<Customer> findByTelephone(String telephone);

    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Optional<Customer> findByEmail(String email);

    default boolean checkIdentityNumber(String identityNumber) throws CustomerByIdentityNumberNotUniqueException {
        Optional<Customer> customer = findByIdentityNumber(identityNumber);
        if (customer.isPresent()) {
            throw new CustomerByIdentityNumberNotUniqueException();
        }
        return true;
    }

    default boolean checkTelephone(String telephone) throws CustomerByTelephoneNotUniqueException {
        Optional<Customer> customer = findByTelephone(telephone);
        if (customer.isPresent()) {
            throw new CustomerByTelephoneNotUniqueException();
        }
        return true;
    }

    default boolean checkEmail(String email) throws CustomerByEmailNotUniqueException {
        Optional<Customer> customer = findByEmail(email);
        if (customer.isPresent()) {
            throw new CustomerByEmailNotUniqueException();
        }
        return true;
    }
    @Query("SELECT c FROM Customer c WHERE c.birthDay <= :birthDate")
    List<Customer> findByBirthDayBefore(Date birthDate);

    default boolean ageLimit(Date birthDate) throws AgeLimitException {
        List<Customer> customers = findByBirthDayBefore(birthDate);
        if (!customers.isEmpty()) {
            throw new AgeLimitException();
        }
        return true;
    }

    Optional<Customer> findByUserNumber(String identity);
}
