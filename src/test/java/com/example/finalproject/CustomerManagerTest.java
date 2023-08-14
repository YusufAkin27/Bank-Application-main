package com.example.finalproject;

import com.example.finalproject.customer.business.concretes.CustomerManager;
import com.example.finalproject.customer.core.request.CreateCustomerRequest;
import com.example.finalproject.customer.core.request.UpdateCustomerRequest;
import com.example.finalproject.customer.core.response.CustomerDTO;
import com.example.finalproject.response.ServiceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class CustomerManagerTest {

    private CustomerManager customerManager;

    @BeforeEach
    public void setUp() {
        // You can initialize the customerManager here or use dependency injection
        // For simplicity, let's assume customerManager is already initialized properly.
    }

    @Test
    public void testAddCustomer() {
        CreateCustomerRequest request = new CreateCustomerRequest();
        // Set the required properties of the request object here

        try {
            ServiceResponse actualServiceResponse = customerManager.singup(request);

            ServiceResponse expectedServiceResponse = new ServiceResponse("Müşteri kaydı başarıyla tamamlandı", true);
            Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("An unexpected exception occurred during addCustomer test.");
        }
    }

    @Test
    public void testDeleteCustomer() {
        long customerId = 1;

        try {
            ServiceResponse actualServiceResponse = customerManager.deleteCustomer(customerId);

            ServiceResponse expectedServiceResponse = new ServiceResponse(customerId + "    ID'si belirtilen müşteri başarıyla silindi", true);
            Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("An unexpected exception occurred during deleteCustomer test.");
        }
    }

    @Test
    public void testUpdateCustomer() {
        UpdateCustomerRequest updateCustomerRequest = new UpdateCustomerRequest();
        // Set the required properties of the updateCustomerRequest object here

        try {
            ServiceResponse actualServiceResponse = customerManager.updateCustomer(userDetail.getUser().getId(), updateCustomerRequest);

            ServiceResponse expectedServiceResponse = new ServiceResponse(updateCustomerRequest.getId() + "    ID'si belirtilen müşteri başarıyla güncellendi", true);
            Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("An unexpected exception occurred during updateCustomer test.");
        }
    }

    @Test
    public void testGetAllCustomer() {
        try {
            List<CustomerDTO> actualResult = customerManager.getAllCustomers();

            ServiceResponse expectedServiceResponse = new ServiceResponse("   tüm müşteriler getirildi", true);
            Assertions.assertEquals(expectedServiceResponse, actualResult);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("An unexpected exception occurred during getAllCustomer test.");
        }
    }

    @Test
    public void testDoActiveCustomer() {
        long customerId = 1;

        try {
            ServiceResponse actualServiceResponse = customerManager.activateCustomer(customerId);

            ServiceResponse expectedServiceResponse = new ServiceResponse(customerId + "    ID'si belirtilen müşteri başarıyla aktifleştirildi", true);
            Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("An unexpected exception occurred during doActiveCustomer test.");
        }
    }

    @Test
    public void testSearchCustomer() {
        String info = "yusuf akin";

        try {
            List<CustomerDTO> actualResult = customerManager.searchCustomers(info);

            ServiceResponse expectedServiceResponse = new ServiceResponse(info + "  belirtilen bilgili müşteriler başarıyla bulundu", true);
            Assertions.assertEquals(expectedServiceResponse, actualResult);
        } catch (Exception e) {
            e.printStackTrace();
            Assertions.fail("An unexpected exception occurred during searchCustomer test.");
        }
    }
}
