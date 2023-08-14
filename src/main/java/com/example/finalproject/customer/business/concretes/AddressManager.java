package com.example.finalproject.customer.business.concretes;


import com.example.finalproject.customer.business.abstarcts.AddressService;
import com.example.finalproject.customer.core.exception.AddressNotFoundException;
import com.example.finalproject.customer.core.exception.CustomerAddressInfoException;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.customer.core.request.CreateAddressRequest;
import com.example.finalproject.customer.core.request.UpdateAddressRequest;
import com.example.finalproject.customer.entity.Address;
import com.example.finalproject.customer.entity.Customer;
import com.example.finalproject.customer.repository.AddressRepository;
import com.example.finalproject.customer.repository.CustomerRepository;
import com.example.finalproject.response.ServiceResponse;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;



@RequiredArgsConstructor
@Service
@Scope("prototype")
public class AddressManager implements AddressService {

    private final AddressRepository addressRepository;

    private final CustomerRepository customerRepository;


    @Override
    @Transactional
    public ServiceResponse createAddress(long customerId,CreateAddressRequest createAddressRequest) throws CustomerNotFoundException,
            CustomerAddressInfoException {
        Customer customer = customerRepository.findByCustomerId(customerId);

        if (customer.getAddress() != null) {
            throw new CustomerAddressInfoException();
        }
        Address address = createAddressConverter(customerId,createAddressRequest);
        customer.setAddress(address);
        addressRepository.save(address);
        return new ServiceResponse("adres kayıt başarılı.", true);

    }

    @Override
    @Transactional
    public ServiceResponse deleteAddress(long customerId) throws CustomerNotFoundException, AddressNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (customer.getAddress() == null) {
            throw new AddressNotFoundException();
        }

        addressRepository.delete(customer.getAddress());
        customer.setAddress(null);
        return new ServiceResponse("adres silme başarılı", true);

    }

    @Override
    public Address getAddress(long customerId) throws CustomerNotFoundException, AddressNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId);
        if (customer.getAddress() == null) {
            throw new AddressNotFoundException();
        }
        return customer.getAddress();
    }

    @Override
    @Transactional
    public ServiceResponse updateAddress(long customerId,UpdateAddressRequest updateAddressRequest) throws CustomerNotFoundException, AddressNotFoundException {
        Address address = checkingAddressConverter(customerId,updateAddressRequest);

        if (address == null) {
            throw new AddressNotFoundException();
        }
        return new ServiceResponse("adres güncelleme başarılı   ", true);

    }

    @Transactional
    public Address checkingAddressConverter(long customerId,UpdateAddressRequest updateAddressRequest) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId);

        if (updateAddressRequest.getCity() != null) {
            customer.getAddress().setCity(updateAddressRequest.getCity());
        }
        if (updateAddressRequest.getCountry() != null) {
            customer.getAddress().setCountry(updateAddressRequest.getCountry());
        }
        if (updateAddressRequest.getStreetNumber() != null) {
            customer.getAddress().setStreetNumber(updateAddressRequest.getStreetNumber());
        }
        return customer.getAddress();
    }



    public Address createAddressConverter(long customerId,CreateAddressRequest createAddressRequest) throws CustomerNotFoundException {
        Customer customer = customerRepository.findByCustomerId(customerId);
        return Address.builder()
                .city(createAddressRequest.getCity())
                .country(createAddressRequest.getCountry())
                .streetNumber(createAddressRequest.getStreetNumber())
                .district(createAddressRequest.getDistrict())
                .customer(customer)
                .build();
    }

}
