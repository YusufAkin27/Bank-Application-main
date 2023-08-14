package com.example.finalproject.customer.controller;


import com.example.finalproject.common.security.user.CustomUserDetail;
import com.example.finalproject.customer.business.abstarcts.CustomerService;
import com.example.finalproject.customer.core.exception.*;
import com.example.finalproject.customer.core.request.CreateCustomerRequest;
import com.example.finalproject.customer.core.request.UpdateCustomerRequest;
import com.example.finalproject.customer.core.response.CustomerDTO;
import com.example.finalproject.customer.entity.Customer;
import com.example.finalproject.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bank/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/sing-up")
    public ServiceResponse create(@RequestBody @Valid CreateCustomerRequest createCustomerRequest)
            throws CustomerByEmailNotUniqueException, AgeLimitException, CustomerByTelephoneNotUniqueException,
            CustomerByIdentityNumberNotUniqueException, Is10TelephoneException, CheckingEmailException,
            Is11IdentityNumberException {
        return customerService.singup(createCustomerRequest);
    }

    @DeleteMapping(path = "/delete")
    public ServiceResponse delete(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail)
            throws BusinessException {
        return customerService.deleteCustomer(userDetail.getUser().getId());

    }

    @PatchMapping(path = "/update")
    public ServiceResponse update(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                  @RequestBody UpdateCustomerRequest updateCustomerRequest) throws
            CustomerNotFoundException, CustomerByEmailNotUniqueException, AgeLimitException,
            CustomerByTelephoneNotUniqueException, CustomerByIdentityNumberNotUniqueException,
            Is10TelephoneException, CheckingEmailException, Is11IdentityNumberException {
        return customerService.updateCustomer(userDetail.getUser().getId(), updateCustomerRequest);
    }
    @GetMapping(path = "/activate")
    public ServiceResponse activateCustomer(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail)
            throws ClientIsAlreadyActive, CustomerNotFoundException {
        return customerService.activateCustomer(userDetail.getUser().getId());
    }
    @GetMapping("/all")
    public List<CustomerDTO> getAllCustomers() throws  CustomerNotFoundException {
        return customerService.getAllCustomers();
    }

    @GetMapping("/getById")
    public Optional<Customer> getCustomerById(@RequestParam(name = "customerId") long customerId) throws  CustomerNotFoundException {
        return customerService.getCustomerById(customerId);
    }

    @GetMapping("/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "customerInfo") String customerInfo) throws  CustomerNotFoundException {
        return customerService.searchCustomers(customerInfo);
    }

    @GetMapping("/getByIdentityNumber")
    public Optional<Customer> getCustomerByIdentityNumber(@RequestParam(name = "identityNumber") String identityNumber)
            throws CustomerNotFoundException {
        return customerService.getCustomerByIdentityNumber(identityNumber);
    }


}
