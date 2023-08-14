package com.example.finalproject.customer.core.response;


import com.example.finalproject.account.core.response.CheckingAccountDTO;
import com.example.finalproject.account.core.response.SavingAccountDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {
    private Long id;
    private String identityNumber;
    private String name;
    private String surname;
    private String telephone;
    private BigDecimal income;
    private String email;
    private String password;
    @Temporal(TemporalType.DATE)
    private Date birthDay;
    private LocalDate createdAt;
    private boolean isActive;
    private AddressDTO address;
    private List<CheckingAccountDTO> checkingAccount = new ArrayList<>();
    private List<SavingAccountDTO>savingAccount=new ArrayList<>();


}
