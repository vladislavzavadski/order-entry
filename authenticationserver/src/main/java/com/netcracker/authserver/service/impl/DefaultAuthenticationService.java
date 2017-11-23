package com.netcracker.authserver.service.impl;

import com.netcracker.authserver.dao.UserDao;
import com.netcracker.authserver.domain.User;
import com.netcracker.authserver.domain.to.UserDTO;
import com.netcracker.authserver.service.AuthenticationService;
import com.netcracker.authserver.service.converter.Converter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DefaultAuthenticationService implements AuthenticationService {

    private final UserDao userDao;
    private final Converter<UserDTO, User> userConverter;

    @Autowired
    public DefaultAuthenticationService(UserDao userDao, Converter<UserDTO, User> userConverter) {
        this.userDao = userDao;
        this.userConverter = userConverter;
    }

    @Override
    public UserDTO findUserByUsername(String username){
        User user = userDao.findOne(username);

        if(user == null){
            return null;
        }

        return userConverter.convert(user);
    }
}
