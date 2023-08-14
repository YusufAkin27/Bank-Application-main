package com.example.finalproject;

import com.example.finalproject.account.business.concretes.CheckingAccountManager;
import com.example.finalproject.account.core.request.CreateCheckingAccountRequest;
import com.example.finalproject.account.core.response.CheckingAccountDTO;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.response.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CheckingAccountManagerTest {

    private final CheckingAccountManager checkingAccountManager;


    @Test
    public void testAddCheckingAccount() {
        CreateCheckingAccountRequest createCheckingAccountRequest = new CreateCheckingAccountRequest();
        createCheckingAccountRequest.setCustomerId(1);

        ServiceResponse expectedServiceResponse = new ServiceResponse("checking account kaydı başarıyla tamamlandı", true);
        ServiceResponse actualServiceResponse = null;
        try {
            actualServiceResponse = checkingAccountManager.add(createCheckingAccountRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
    }

    @Test
    public void testDeleteCheckingAccount() {
        long checkingAccountId = 1;long customerId = 1;
        ServiceResponse expectedServiceResponse = new ServiceResponse(checkingAccountId + "    ID'si belirtilen checking account hesabı başarıyla silindi", true);
        ServiceResponse actualServiceResponse = null;
        try {
            actualServiceResponse = checkingAccountManager.delete(checkingAccountId,customerId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
    }


    @Test
    public void testGetAllCustomer() {

        ServiceResponse expectedServiceResponse = new ServiceResponse("   tüm checking account hesapları getirildi", true);
        List<CheckingAccountDTO> actualResult = new ArrayList<>();
        try {
            actualResult = checkingAccountManager.getAllWithPagination(1,10);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualResult);
    }


    @Test
    public void testDoActiveCustomer() {

        long checkingAccountId = 1;

        ServiceResponse expectedServiceResponse = new ServiceResponse(checkingAccountId + "    ID'si belirtilen müşteri başarıyla aktifleştirildi", true);
        ServiceResponse actualServiceResponse = null;
        try {
            actualServiceResponse = checkingAccountManager.activate(checkingAccountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
    }
    @Test
    public void testGetAllActivity() {

        long checkingAccountId = 1;

        ServiceResponse expectedServiceResponse = new ServiceResponse("Checking hesabının tüm aktiviteleri getirildi", true);
        List<Activity> actualResult = null;
        try {
            actualResult = checkingAccountManager.activateWithPagination(checkingAccountId, 1,20);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualResult);
    }

}