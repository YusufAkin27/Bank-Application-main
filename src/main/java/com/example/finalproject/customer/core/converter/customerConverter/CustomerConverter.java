package com.example.finalproject.customer.core.converter.customerConverter;

import com.example.finalproject.customer.core.request.CreateCustomerRequest;
import com.example.finalproject.customer.core.response.CustomerDTO;
import com.example.finalproject.customer.entity.Customer;

public interface CustomerConverter {
    Customer createToCustomer(CreateCustomerRequest createCustomerRequest);

    CustomerDTO customerDTOConverter(Customer customer);



}
