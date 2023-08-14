package com.example.finalproject.customer.business.abstarcts;

import com.example.finalproject.customer.core.exception.*;
import com.example.finalproject.customer.core.request.CreateCustomerRequest;
import com.example.finalproject.customer.core.request.UpdateCustomerRequest;
import com.example.finalproject.customer.core.response.CustomerDTO;
import com.example.finalproject.customer.entity.Customer;
import com.example.finalproject.response.ServiceResponse;

import java.util.List;
import java.util.Optional;


public interface CustomerService {
    ServiceResponse singup(CreateCustomerRequest createCustomerRequest) throws CustomerByIdentityNumberNotUniqueException, CustomerByEmailNotUniqueException,
            CustomerByTelephoneNotUniqueException, AgeLimitException, Is10TelephoneException, Is11IdentityNumberException, CheckingEmailException;

    ServiceResponse deleteCustomer(long costumerId) throws BusinessException;

    ServiceResponse updateCustomer(long id, UpdateCustomerRequest updateCustomerRequest)
            throws CustomerNotFoundException, AgeLimitException, CustomerByIdentityNumberNotUniqueException,
            CustomerByEmailNotUniqueException, CustomerByTelephoneNotUniqueException, Is11IdentityNumberException,
            Is10TelephoneException, CheckingEmailException;

    List<CustomerDTO> getAllCustomers() throws CustomerNotFoundException;

    Optional<Customer> getCustomerById( long customerId) throws CustomerNotFoundException;

    ServiceResponse activateCustomer(long customerId) throws ClientIsAlreadyActive, CustomerNotFoundException;

    List<CustomerDTO> searchCustomers( String customerInfo) throws CustomerNotFoundException;

    Optional<Customer> getCustomerByIdentityNumber( String identityNumber) throws CustomerNotFoundException;

}
