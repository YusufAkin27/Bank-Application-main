package com.example.finalproject.account.rules;


import com.example.finalproject.account.core.exception.checkingAccountExcepiton.AddressCannotBeEmptyException;
import com.example.finalproject.customer.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CheckingAccountRules {


    public boolean addressNotEmpty(Customer customer) throws AddressCannotBeEmptyException {
        if (customer.getAddress() == null) {
            throw new AddressCannotBeEmptyException();
        }
        return false;
    }
}
