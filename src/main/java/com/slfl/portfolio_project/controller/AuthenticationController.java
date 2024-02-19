package com.slfl.portfolio_project.controller;

import com.slfl.portfolio_project.model.RegistrationDTO;
import com.slfl.portfolio_project.model.ResponseStatus;
import com.slfl.portfolio_project.service.AuthenticationService;
import com.slfl.portfolio_project.service.AuthenticationServiceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class  AuthenticationController {

    @Autowired
    private AuthenticationServiceWrapper authenticationService;

    @PostMapping("/register")
    public ResponseStatus registerUser(@RequestBody RegistrationDTO body) {
        try {
            ResponseStatus resultCheck = authenticationService.checkExistUser(body.getUsername(), body.getEmail());
            if (resultCheck.getCode().equals("200")) {
                resultCheck = authenticationService.registerUser(body.getUsername(), body.getEmail(), body.getPassword(), body.isPhotographer());
            }
            return resultCheck;
        } catch (RuntimeException ex) {
            return new ResponseStatus().generalError(ex);
        }
    }

    @PostMapping("/login")
    public ResponseStatus loginUser(@RequestBody RegistrationDTO body) {
        ResponseStatus resultCheck = authenticationService.checkExistUserLogin(body.getUsername());
        if (resultCheck.getCode().equals("200")) {
            resultCheck = authenticationService.loginUser(body.getUsername(), body.getPassword());
        }
        return resultCheck;
    }
}