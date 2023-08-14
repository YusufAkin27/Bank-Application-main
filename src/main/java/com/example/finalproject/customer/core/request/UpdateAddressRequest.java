package com.example.finalproject.customer.core.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateAddressRequest {

    @Size(min = 3, max = 15)
    private String country;

    @Size(min = 3, max = 15)
    private String city;

    @Size(min = 3, max = 15)
    private String district;

    @Size(min = 3, max = 50)
    private String streetNumber;
}
