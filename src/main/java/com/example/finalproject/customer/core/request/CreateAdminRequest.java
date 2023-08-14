package com.example.finalproject.customer.core.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAdminRequest {
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
    private Date birthDay;

    @NotNull(message = "Telephone Cannot be null.")
    private String telephone;


}
