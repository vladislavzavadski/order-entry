package com.netcracker.orderentry.catalog.client;


import com.netcracker.orderentry.catalog.domain.to.UserDTO;

public interface AuthenticationClient {

    UserDTO getUserByUsername(String username);

}
