package com.example.finalproject;

import com.example.finalproject.card.business.concretes.DebitCardManager;
import com.example.finalproject.card.core.request.CreateDebitCardRequest;
import com.example.finalproject.card.core.response.DebitCardDTO;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.response.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class DebitCardManagerTest {

    private final DebitCardManager debitCardManager;


    @Test
    public void testAddDebitCard() {
        CreateDebitCardRequest createDebitCardRequest = new CreateDebitCardRequest();
        createDebitCardRequest.setCheckingAccountId(1);
        createDebitCardRequest.setPassword("1234");

        ServiceResponse expectedServiceResponse = new ServiceResponse("banka kartı kaydı başarıyla tamamlandı", true);
        ServiceResponse actualServiceResponse = null;
        try {
            actualServiceResponse = debitCardManager.add(createDebitCardRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
    }

    @Test
    public void testDeleteDebitCard() {
        long debitCardId = 1;
        ServiceResponse expectedServiceResponse = new ServiceResponse(debitCardId + "    ID'si belirtilen banka kartı başarıyla silindi", true);
        ServiceResponse actualServiceResponse = null;
        try {
            actualServiceResponse = debitCardManager.delete(debitCardId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
    }


    @Test
    public void testGetAllDebitCard() {

        ServiceResponse expectedServiceResponse = new ServiceResponse("   tüm banka kartı hesapları getirildi", true);
        List<DebitCardDTO> actualResult = new ArrayList<>();
        try {
            actualResult = debitCardManager.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualResult);
    }



    @Test
    public void testGetAllActivity() {

        long debitCardId = 1;

        ServiceResponse expectedServiceResponse = new ServiceResponse(" banka kartı hesabının tüm aktiviteleri getirildi", true);
        List<Activity> actualResult = null;
        try {
            actualResult = debitCardManager.getAllActivity(debitCardId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualResult);
    }
}