package com.example.finalproject.customer.controller;

import com.example.finalproject.common.security.user.CustomUserDetail;
import com.example.finalproject.customer.business.abstarcts.AddressService;
import com.example.finalproject.customer.core.exception.AddressNotFoundException;
import com.example.finalproject.customer.core.exception.CustomerAddressInfoException;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.customer.core.request.CreateAddressRequest;
import com.example.finalproject.customer.core.request.UpdateAddressRequest;

import com.example.finalproject.customer.entity.Address;
import com.example.finalproject.response.ServiceResponse;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bank/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/create")
    public ServiceResponse add(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                               @RequestBody CreateAddressRequest createAddressRequest) throws CustomerNotFoundException,
            CustomerAddressInfoException {
        return addressService.createAddress(userDetail.getUser().getId(),createAddressRequest);
    }

    @PutMapping("/update")
    public ServiceResponse update(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail,
                                  @RequestBody UpdateAddressRequest updateAddressRequest) throws CustomerNotFoundException,
            AddressNotFoundException {
        return addressService.updateAddress(userDetail.getUser().getId(),updateAddressRequest);
    }

    @DeleteMapping("/delete")
    public ServiceResponse delete(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail) throws CustomerNotFoundException,
            CustomerAddressInfoException, AddressNotFoundException {
        return addressService.deleteAddress(userDetail.getUser().getId());
    }

    @GetMapping("/get")
    public Address getAddress(@Parameter(hidden = true) @AuthenticationPrincipal CustomUserDetail userDetail) throws CustomerAddressInfoException,
            CustomerNotFoundException, AddressNotFoundException {
        return addressService.getAddress(userDetail.getUser().getId());
    }

}
