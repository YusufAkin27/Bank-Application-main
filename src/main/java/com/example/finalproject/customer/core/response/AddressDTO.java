package com.example.finalproject.customer.core.response;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressDTO {
    private long id;
    private String country;
    private String city;
    private String state;
    private String district;
    private String streetNumber;

}
