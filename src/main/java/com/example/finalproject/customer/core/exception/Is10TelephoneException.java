package com.example.finalproject.customer.core.exception;

public class Is10TelephoneException extends BusinessException {

    public Is10TelephoneException() {
        super("Geçersiz Telefon Numarası Biçimi\"+\n" +
                "                \"Lütfen geçerli bir telefon numarası giriniz. Telefon numarası 10 hane uzunluğunda olmalıdır..");
    }
}
