package com.example.finalproject.customer.business.abstarcts;

import com.example.finalproject.customer.core.exception.AddressNotFoundException;
import com.example.finalproject.customer.core.exception.CustomerAddressInfoException;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.customer.core.request.CreateAddressRequest;
import com.example.finalproject.customer.core.request.UpdateAddressRequest;
import com.example.finalproject.customer.entity.Address;
import com.example.finalproject.response.ServiceResponse;


public interface AddressService {
    ServiceResponse createAddress(long customerId,CreateAddressRequest createAddressRequest) throws CustomerNotFoundException, CustomerAddressInfoException;

    ServiceResponse deleteAddress(long customerId) throws CustomerNotFoundException, CustomerAddressInfoException, AddressNotFoundException;

    Address getAddress(long customerId) throws CustomerNotFoundException, CustomerAddressInfoException, AddressNotFoundException;

    ServiceResponse updateAddress(long customerId,UpdateAddressRequest updateAddressRequest) throws CustomerNotFoundException, AddressNotFoundException;


}
