package com.example.finalproject;

import com.example.finalproject.customer.business.abstarcts.AddressService;
import com.example.finalproject.customer.controller.AddressController;
import com.example.finalproject.customer.core.exception.AddressNotFoundException;
import com.example.finalproject.customer.core.exception.CustomerAddressInfoException;
import com.example.finalproject.customer.core.exception.CustomerNotFoundException;
import com.example.finalproject.customer.core.request.CreateAddressRequest;
import com.example.finalproject.customer.core.request.UpdateAddressRequest;
import com.example.finalproject.customer.entity.Address;
import com.example.finalproject.response.ServiceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AddressControllerTest {

    private AddressController addressController;
    private AddressService addressService;

    @BeforeEach
    public void setUp() {
        addressService = mock(AddressService.class);
        addressController = new AddressController(addressService);
    }

    @Test
    public void testAddAddress() throws CustomerNotFoundException, CustomerAddressInfoException {
        CreateAddressRequest request = new CreateAddressRequest();
        request.setCustomerId(1);
        request.setCountry("Country");
        request.setCity("City");
        request.setDistrict("District");
        request.setStreetNumber("StreetNumber");

        ServiceResponse expectedServiceResponse = new ServiceResponse("Address added successfully", true);
        when(addressService.createAddress(request)).thenReturn(expectedServiceResponse);

        ServiceResponse actualServiceResponse = addressController.add(request);

        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
    }

    @Test
    public void testUpdateAddress() throws CustomerNotFoundException, AddressNotFoundException {
        UpdateAddressRequest request = new UpdateAddressRequest();
        request.setCustomerId(1);
        request.setCountry("Updated Country");
        request.setCity("Updated City");
        request.setDistrict("Updated District");
        request.setStreetNumber("Updated StreetNumber");

        ServiceResponse expectedServiceResponse = new ServiceResponse("Address updated successfully", true);
        when(addressService.updateAddress(request)).thenReturn(expectedServiceResponse);

        ServiceResponse actualServiceResponse = addressController.update(request);

        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
    }

    @Test
    public void testDeleteAddress() throws CustomerNotFoundException, CustomerAddressInfoException, AddressNotFoundException {
        long customerId = 1;
        ServiceResponse expectedServiceResponse = new ServiceResponse("Address deleted successfully", true);
        when(addressService.deleteAddress(customerId)).thenReturn(expectedServiceResponse);

        ServiceResponse actualServiceResponse = addressController.delete(customerId);

        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
    }

    @Test
    public void testGetAddress() throws CustomerAddressInfoException, CustomerNotFoundException, AddressNotFoundException {
        Long customerId = 1L;
        Address expectedAddress = new Address();
        expectedAddress.setId(1);
        expectedAddress.setCountry("Country");
        expectedAddress.setCity("City");
        expectedAddress.setDistrict("District");
        expectedAddress.setStreetNumber("StreetNumber");

        when(addressService.getAddress(customerId)).thenReturn(expectedAddress);

        Address actualAddress = addressController.getAddress(customerId);

        Assertions.assertEquals(expectedAddress, actualAddress);
    }

}
