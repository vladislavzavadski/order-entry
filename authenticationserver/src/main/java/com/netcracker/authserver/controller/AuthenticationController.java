package com.netcracker.authserver.controller;

import com.netcracker.authserver.domain.to.UserDTO;
import com.netcracker.authserver.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.GET)
    public UserDTO findUser(@RequestParam("username") String username){
        return authenticationService.findUserByUsername(username);
    }

}
