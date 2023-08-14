package com.example.finalproject;

import com.example.finalproject.account.business.concretes.SavingAccountManager;
import com.example.finalproject.account.core.request.AddMoneySavingAccount;
import com.example.finalproject.account.core.request.CreateSavingAccountRequest;
import com.example.finalproject.account.core.response.SavingAccountDTO;
import com.example.finalproject.account.entity.enums.Maturity;
import com.example.finalproject.card.entity.base.Activity;
import com.example.finalproject.response.ServiceResponse;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class SavingAccountManagerTest {

    private final SavingAccountManager savingAccountManager;


    @Test
    public void testAddSavingAccount() {
        CreateSavingAccountRequest createSavingAccountRequest = new CreateSavingAccountRequest();
        createSavingAccountRequest.setCustomerId(1);

        ServiceResponse expectedServiceResponse = new ServiceResponse("tasarruf hesabı kaydı başarıyla tamamlandı", true);
        ServiceResponse actualServiceResponse = null;
        try {
            actualServiceResponse = savingAccountManager.add(createSavingAccountRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
    }

    @Test
    public void testDeleteSavingAccount() {
        long savingAccountId = 1;
        ServiceResponse expectedServiceResponse = new ServiceResponse(savingAccountId + "    ID'si belirtilen tasarruf  hesabı başarıyla silindi", true);
        ServiceResponse actualServiceResponse = null;
        try {
            actualServiceResponse = savingAccountManager.delete(savingAccountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
    }


    @Test
    public void testGetAllSavingAccount() {

        ServiceResponse expectedServiceResponse = new ServiceResponse("   tüm tasarruf hesapları getirildi", true);
        List<SavingAccountDTO> actualResult = new ArrayList<>();
        try {
            actualResult = savingAccountManager.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualResult);
    }


    @Test
    public void testAddMoneySavingAccount() {
        AddMoneySavingAccount addMoneySavingAccount=new AddMoneySavingAccount();
        addMoneySavingAccount.setSavingAccountId(1);
        addMoneySavingAccount.setMaturity(Maturity.ALTMIS_GUN);
        addMoneySavingAccount.setAmount(BigDecimal.valueOf(10000));


        ServiceResponse expectedServiceResponse = new ServiceResponse( "    tasarruf hesabına para yatırıldı", true);
        ServiceResponse actualServiceResponse = null;
        try {
            actualServiceResponse = savingAccountManager.addMoney(addMoneySavingAccount);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
    }

    @Test
    public void testGetAllActivity() {
        long savingAccountId = 1;
        ServiceResponse expectedServiceResponse = new ServiceResponse(" tasarruf hesabının tüm aktiviteleri getirildi", true);
        List<Activity> actualResult = null;
        try {
            actualResult = savingAccountManager.getActivities(savingAccountId);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Assertions.assertEquals(expectedServiceResponse, actualResult);
    }
}