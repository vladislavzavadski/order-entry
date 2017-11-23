package com.netcracker.authserver.service;

import com.netcracker.authserver.domain.to.UserDTO;

public interface AuthenticationService {
    UserDTO findUserByUsername(String usernmae);
}
