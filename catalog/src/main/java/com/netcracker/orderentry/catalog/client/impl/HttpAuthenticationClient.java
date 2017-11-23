package com.netcracker.orderentry.catalog.client.impl;


import com.netcracker.orderentry.catalog.client.AuthenticationClient;
import com.netcracker.orderentry.catalog.domain.to.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class HttpAuthenticationClient implements AuthenticationClient {

    private final RestTemplate restTemplate;

    @Autowired
    public HttpAuthenticationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDTO getUserByUsername(String username) {
        ResponseEntity<UserDTO> responseEntity = restTemplate.getForEntity("http://localhost:8000/authenticate?username="+username, UserDTO.class);
        return responseEntity.getBody();
    }
}
