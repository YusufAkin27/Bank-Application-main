package com.example.finalproject;

import com.example.finalproject.card.core.exception.debitCardExceptions.DebitCardNotActiveException;
import com.example.finalproject.card.core.exception.debitCardExceptions.NotFoundDebitCardException;
import com.example.finalproject.transfer.business.abstracts.TransferService;
import com.example.finalproject.transfer.controller.TransferController;
import com.example.finalproject.transfer.entity.Transfer;
import com.example.finalproject.transfer.exception.*;
import com.example.finalproject.response.ServiceResponse;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

public class TransferControllerTest {

    private TransferController transferController;
    private TransferService transferService;

    @BeforeEach
    public void setUp() {
        transferService = mock(TransferService.class);
        transferController = new TransferController(transferService);
    }

    @Test
    public void testSendMoneySuccess() throws NotFoundDebitCardException, DebitCardNotActiveException, WrongNameAndSurnameException, InsufficientBalanceException, TransferAmountOutOfRangeException, TransferTimeNotAllowedException {
        Transfer transfer = new Transfer();

        ServiceResponse expectedServiceResponse = new ServiceResponse("Transfer process successful: " + transfer.getAmount() + " value transferred", true);

        when(transferService.sendMoney(transfer)).thenReturn(expectedServiceResponse);

        ServiceResponse actualServiceResponse = transferController.sendMoney(transfer);

        Assertions.assertEquals(expectedServiceResponse, actualServiceResponse);
        verify(transferService, times(1)).sendMoney(transfer);
    }

    @Test
    public void testSendMoneyNotFoundDebitCardException() throws NotFoundDebitCardException, DebitCardNotActiveException, WrongNameAndSurnameException, InsufficientBalanceException, TransferAmountOutOfRangeException, TransferTimeNotAllowedException {
        Transfer transfer = new Transfer();

        when(transferService.sendMoney(transfer)).thenThrow(new NotFoundDebitCardException());

        Assertions.assertThrows(NotFoundDebitCardException.class, () -> transferController.sendMoney(transfer));
        verify(transferService, times(1)).sendMoney(transfer);
    }


    @Test
    public void testGetTransferDetailsSuccess() throws NotFoundTransferException {
        long transferId = 1;
        Transfer expectedTransfer = new Transfer();

        when(transferService.getTransferDetails(transferId)).thenReturn(expectedTransfer);

        Transfer actualTransfer = transferController.getTransferDetails(transferId);

        Assertions.assertEquals(expectedTransfer, actualTransfer);
        verify(transferService, times(1)).getTransferDetails(transferId);
    }

    @Test
    public void testGetTransferDetailsNotFoundTransferException() throws NotFoundTransferException {
        long transferId = 1;

        when(transferService.getTransferDetails(transferId)).thenThrow(new NotFoundTransferException());

        Assertions.assertThrows(NotFoundTransferException.class, () -> transferController.getTransferDetails(transferId));
        verify(transferService, times(1)).getTransferDetails(transferId);
    }

    @Test
    public void testGetAllTransfersSuccess() {
        List<Transfer> expectedTransfers = new ArrayList<>();


        when(transferService.getAllTransfers()).thenReturn(expectedTransfers);

        List<Transfer> actualTransfers = transferController.getAllTransfers();

        Assertions.assertEquals(expectedTransfers, actualTransfers);
        verify(transferService, times(1)).getAllTransfers();
    }


}
