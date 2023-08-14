package com.example.finalproject.customer.core.request;



import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.*;
import java.math.BigDecimal;
import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCustomerRequest {
    @NotNull(message = "Identity number cannot be null.")
    private String identityNumber;

    @NotNull(message = "name cannot be null.")
    private String name;
    @NotNull(message = "Surname cannot be null.")
    private String surname;
    @NotNull
    private String email;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, max = 6, message = "password size must be 6 digit.")
    private String password;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @NotNull(message = "Date Cannot be null.")
    private Date birthDay; // 1999-03-03

    @NotNull(message = "Telephone Cannot be null.")
    private String telephone;

    @NotNull(message = "Income Cannot be null.")
    @Min(0)
    private BigDecimal income;


}
