package com.example.finalproject.customer.rules;

import com.example.finalproject.customer.core.exception.*;
import com.example.finalproject.customer.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class CustomerRules {

    private final CustomerRepository customerRepository;

    public Boolean findByIdentityNumber(String identityNumber) throws CustomerByIdentityNumberNotUniqueException {
        return customerRepository.checkIdentityNumber(identityNumber);
    }

    public boolean findByTelephone(String telephone) throws CustomerByTelephoneNotUniqueException {
        return customerRepository.checkTelephone(telephone);
    }

    public boolean findByEmail(String email) throws CustomerByEmailNotUniqueException {
        return customerRepository.checkEmail(email);
    }

    public boolean ageLimit(Date birthday) throws AgeLimitException {
        customerRepository.ageLimit(birthday);
        return true;
    }

    private static final Pattern KIMLIK_NUMARASI_PATTERN = Pattern.compile("^[0-9]{11}$");

    public static boolean identityNumberValidate(String kimlikNumarasi) throws Is11IdentityNumberException {
        Matcher matcher = KIMLIK_NUMARASI_PATTERN.matcher(kimlikNumarasi);
        if (!matcher.matches()) {
            throw new Is11IdentityNumberException();
        }
        return true;
    }
    public boolean isUniqueEmail(String email) throws CustomerByEmailNotUniqueException {
        if (customerRepository.checkEmail(email)) {
            throw new CustomerByEmailNotUniqueException();
        }
        return true;
    }
    public boolean isUniqueTelephoneNumber(String telephone) throws CustomerByTelephoneNotUniqueException {
        if (customerRepository.checkTelephone(telephone)) {
            throw new CustomerByTelephoneNotUniqueException();
        }
        return true;
    }
    public boolean isUniqueIdentityNumber(String identityNumber) throws CustomerByIdentityNumberNotUniqueException {
        if (customerRepository.checkIdentityNumber(identityNumber)) {
            throw new CustomerByIdentityNumberNotUniqueException();
        }
        return true;
    }
    private static final Pattern TELEFON_NUMARASI_PATTERN =
            Pattern.compile("^((\\(\\d{3}\\))|\\d{3})[- .]?\\d{3}[- .]?\\d{4}$");

    public static boolean telephoneNumberValidate(String telephoneNumber) throws Is10TelephoneException {
        Matcher matcher = TELEFON_NUMARASI_PATTERN.matcher(telephoneNumber);
        if (!matcher.matches()) {
            throw new Is10TelephoneException();
        }
        return true;
    }

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    public static boolean checkingEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }
}
