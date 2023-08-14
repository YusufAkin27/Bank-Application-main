package com.example.finalproject.customer.business.concretes;


import com.example.finalproject.customer.business.abstarcts.CustomerService;
import com.example.finalproject.customer.business.abstarcts.RoleService;
import com.example.finalproject.customer.core.constant.CustomerConstant;
import com.example.finalproject.customer.core.converter.customerConverter.CustomerConverter;
import com.example.finalproject.customer.core.exception.*;

import com.example.finalproject.customer.core.request.CreateCustomerRequest;
import com.example.finalproject.customer.core.request.UpdateCustomerRequest;
import com.example.finalproject.customer.core.response.CustomerDTO;
import com.example.finalproject.customer.entity.Customer;
import com.example.finalproject.customer.entity.Role;
import com.example.finalproject.customer.repository.CustomerRepository;
import com.example.finalproject.customer.rules.CustomerRules;
import com.example.finalproject.response.ServiceResponse;

import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerManager implements CustomerService {

    private final CustomerRepository customerRepository;

    private final CustomerConverter customerConverter;

    private final CustomerRules customerRules;

    private final RoleService roleService;


    @Override
    @Transactional
    public ServiceResponse singup(CreateCustomerRequest createCustomerRequest) throws CustomerByIdentityNumberNotUniqueException,
            CustomerByEmailNotUniqueException, CustomerByTelephoneNotUniqueException, AgeLimitException, Is10TelephoneException,
            Is11IdentityNumberException {
        customerRules.identityNumberValidate(createCustomerRequest.getIdentityNumber());
        customerRules.checkingEmail(createCustomerRequest.getEmail());
        customerRules.telephoneNumberValidate(createCustomerRequest.getTelephone());
        customerRules.findByIdentityNumber(createCustomerRequest.getIdentityNumber());
        customerRules.ageLimit(createCustomerRequest.getBirthDay());
        customerRules.findByEmail(createCustomerRequest.getEmail());
        customerRules.findByTelephone(createCustomerRequest.getTelephone());
        Customer customer = customerConverter.createToCustomer(createCustomerRequest);
        customer.setActive(true);
        Role roleUser = roleService.getRoleByName(CustomerConstant.ROLE_USER);
        customer.setRoles(Set.of(roleUser));
        customerRepository.save(customer);
        return new ServiceResponse("müşteri kayıt başarılı", true);
    }


    @Override
    @Transactional
    public ServiceResponse deleteCustomer(long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId);
        customer.setActive(false);
        return new ServiceResponse(customerId + "ID li müşteri silme işlemi başarılı.", true);
    }


    @Override
    @Transactional
    public ServiceResponse updateCustomer(long id, UpdateCustomerRequest updateCustomerRequest) throws CustomerNotFoundException,
            CustomerByEmailNotUniqueException,
            CustomerByTelephoneNotUniqueException, Is10TelephoneException {
        Customer customer = customerRepository.findByCustomerId(id);


        if (updateCustomerRequest.getEmail() != null) {
            customerRules.findByEmail(updateCustomerRequest.getEmail());
            customerRules.checkingEmail(updateCustomerRequest.getEmail());
        }
        if (updateCustomerRequest.getTelephone() != null) {
            customerRules.findByTelephone(updateCustomerRequest.getTelephone());
            customerRules.telephoneNumberValidate(updateCustomerRequest.getTelephone());
        }
        customer = checkingCustomer(updateCustomerRequest, customer);
        return new ServiceResponse("güncelleme işlemi başarılı.", true);

    }


    @Override
    public List<CustomerDTO> getAllCustomers()  {
        List<Customer> customers = customerRepository.findAll();
        List<CustomerDTO> customerList = customers.stream()
                .filter(Customer::isActive)
                .map(customerConverter::customerDTOConverter)
                .collect(Collectors.toList());
        return customerList;
    }

    @Override
    public Optional<Customer> getCustomerById( long customerId)  {
        return customerRepository.findById(customerId);

    }

    @Override
    @Transactional
    public ServiceResponse activateCustomer(long customerId) throws ClientIsAlreadyActive, CustomerNotFoundException {
        Optional<Customer> customer = customerRepository.findById(customerId);

        if (customer.get().isActive()) {
            throw new ClientIsAlreadyActive();
        }
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        customer.get().setActive(true);
        return new ServiceResponse(customerId + "  Belirtilen müşteri kimliği artık etkin durumda.", true);

    }


    @Override
    public List<CustomerDTO> searchCustomers( String customerInfo)  {


        List<Customer> customers = customerRepository.search(customerInfo);
        List<CustomerDTO> customerList = customers.stream()
                .map(customerConverter::customerDTOConverter)
                .collect(Collectors.toList());
        return customerList;
    }


    @Override
    public Optional<Customer> getCustomerByIdentityNumber( String identityNumber) throws CustomerNotFoundException {


        return customerRepository.getByIdentityNumber(identityNumber);
    }



    public Customer checkingCustomer(UpdateCustomerRequest updateCustomerRequest, Customer customer) {

        if (updateCustomerRequest.getTelephone() != null) {
            customer.setTelephone(updateCustomerRequest.getTelephone());
        }

        if (updateCustomerRequest.getIncome() != null) {
            customer.setIncome(updateCustomerRequest.getIncome());
        }

        if (updateCustomerRequest.getEmail() != null) {
            customer.setEmail(updateCustomerRequest.getEmail());
        }


        return customer;

    }

}





