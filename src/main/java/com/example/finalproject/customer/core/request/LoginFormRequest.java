package com.example.finalproject.customer.core.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class LoginFormRequest {
    @NotNull(message = "Identity number cannot be null.")
    @Size(min = 11, max = 11, message = "Identity size must be 11 digit.")
    @JsonProperty(value = "identity")
    private String userNumber;

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, max = 6, message = "password size must be 6 digit.")
    private String password;

}
