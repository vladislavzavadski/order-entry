package com.netcracker.orderentry.processor.client;

import com.netcracker.orderentry.processor.domain.to.UserDTO;

public interface AuthenticationClient {

    UserDTO getUserByUsername(String username);

}
