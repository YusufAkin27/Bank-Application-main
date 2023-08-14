package com.example.finalproject.customer.core.converter.addressConverter;

import com.example.finalproject.customer.core.response.AddressDTO;
import com.example.finalproject.customer.entity.Address;

public interface AddressConverter {
    AddressDTO toAddressDTO(Address address);
}
