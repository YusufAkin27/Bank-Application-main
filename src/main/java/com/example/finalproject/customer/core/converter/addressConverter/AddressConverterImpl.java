package com.example.finalproject.customer.core.converter.addressConverter;



import com.example.finalproject.customer.core.response.AddressDTO;
import com.example.finalproject.customer.entity.Address;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;


@Service
public class AddressConverterImpl implements AddressConverter {
    @Override
    public AddressDTO toAddressDTO(Address address) {
        return AddressDTO.builder()
                .id(address.getId())
                .city(address.getCity())
                .country(address.getCountry())
                .streetNumber(address.getStreetNumber())
                .district(address.getDistrict()).build();
    }
}
