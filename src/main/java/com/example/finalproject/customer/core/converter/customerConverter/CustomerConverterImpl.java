package com.example.finalproject.customer.core.converter.customerConverter;

import com.example.finalproject.account.core.converter.CheckingAccountConverter;
import com.example.finalproject.account.core.converter.SavingAccountConverter;
import com.example.finalproject.account.core.response.CheckingAccountDTO;
import com.example.finalproject.account.core.response.SavingAccountDTO;
import com.example.finalproject.customer.core.converter.addressConverter.AddressConverter;
import com.example.finalproject.customer.core.request.CreateCustomerRequest;
import com.example.finalproject.customer.core.response.CustomerDTO;
import com.example.finalproject.customer.entity.Customer;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class CustomerConverterImpl implements CustomerConverter {

    private final AddressConverter addressConverter;
    private final CheckingAccountConverter checkingAccountConverter;
    private final SavingAccountConverter savingAccountConverter;
    private final PasswordEncoder passwordEncoder;

    public Customer createToCustomer(CreateCustomerRequest createCustomerRequest) {
        Customer customer = Customer.builder()
                .name(createCustomerRequest.getName())
                .surname(createCustomerRequest.getSurname())
                .birthDay(createCustomerRequest.getBirthDay())
                .email(createCustomerRequest.getEmail())
                .income(createCustomerRequest.getIncome())
                .telephone(createCustomerRequest.getTelephone())
                .identityNumber(createCustomerRequest.getIdentityNumber())
                .createdAt(LocalDate.now())
                .build();
        customer.setPassword(passwordEncoder.encode(createCustomerRequest.getPassword()));
        customer.setUserNumber(customer.getIdentityNumber());
        return customer;
    }

    public CustomerDTO customerDTOConverter(Customer customer) {
        List<CheckingAccountDTO> checkingAccountDTOS = customer.getCheckingAccounts().stream()
                .map(checkingAccountConverter::toDTO)
                .collect(Collectors.toList());

        List<SavingAccountDTO> savingAccountDTOS = customer.getSavingAccounts().stream()
                .map(savingAccountConverter::toDTO)
                .collect(Collectors.toList());

        return CustomerDTO.builder()
                .id(customer.getId())
                .identityNumber(customer.getIdentityNumber())
                .name(customer.getName())
                .surname(customer.getSurname())
                .telephone(customer.getTelephone())
                .income(customer.getIncome())
                .email(customer.getEmail())
                .isActive(customer.isActive())
                .birthDay(customer.getBirthDay())
                .createdAt(customer.getCreatedAt())
                .address(customer.getAddress() != null ? addressConverter.toAddressDTO(customer.getAddress()) : null)
                .checkingAccount(checkingAccountDTOS)
                .savingAccount(savingAccountDTOS)
                .build();
    }




}
