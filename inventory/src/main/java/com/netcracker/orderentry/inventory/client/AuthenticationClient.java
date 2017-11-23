package com.netcracker.orderentry.inventory.client;


import com.netcracker.orderentry.inventory.domain.to.UserDTO;

public interface AuthenticationClient {

    UserDTO getUserByUsername(String username);

}
