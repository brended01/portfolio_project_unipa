package com.slfl.portfolio_project.service;

import java.util.*;

import com.slfl.portfolio_project.model.*;
import com.slfl.portfolio_project.repository.RoleRepository;
import com.slfl.portfolio_project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@Slf4j
public class AuthenticationServiceWrapper {

    private final AuthenticationService authenticationService;

    public AuthenticationServiceWrapper(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    public ResponseStatus registerUser(String username, String email, String password, boolean isPhotographer) {
        return authenticationService.registerUser(username,email,password,isPhotographer);
    }

    public ResponseStatus loginUser(String username, String password) {
        ResponseStatus responseStatus = authenticationService.loginUser(username,password);
        if(responseStatus.getCode() == "200"){
            log.info("L'utente " + username + " ha effettuato correttamente l'accesso");
        }else{
            log.info("L'utente " + username + " ha avuto problemi ad accedere!");

        }
        return responseStatus;
    }

    public ResponseStatus checkExistUser(String username, String email) {
        return authenticationService.checkExistUser(username, email);
    }

    public ResponseStatus checkExistUserLogin(String username) {
        return authenticationService.checkExistUserLogin(username);
    }
}