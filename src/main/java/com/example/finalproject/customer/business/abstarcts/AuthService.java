package com.example.finalproject.customer.business.abstarcts;



import com.example.finalproject.customer.core.exception.LoginFailedException;
import com.example.finalproject.customer.core.exception.NotFoundIdentityNumberException;
import com.example.finalproject.response.ServiceResponse;

public interface AuthService {

    ServiceResponse login(String identity, String password) throws LoginFailedException, NotFoundIdentityNumberException;

}
