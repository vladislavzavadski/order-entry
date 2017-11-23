package com.netcracker.authserver.service.converter.impl;

import com.netcracker.authserver.domain.User;
import com.netcracker.authserver.domain.to.PermissionDTO;
import com.netcracker.authserver.domain.to.UserDTO;
import com.netcracker.authserver.service.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class UserConverter implements Converter<UserDTO, User> {

    @Override
    public UserDTO convert(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setPassword(user.getPassword());
        userDTO.setUsername(user.getUsername());
        userDTO.setPermissionDTOList(user.getPermissions().stream().map(e -> new PermissionDTO(e.getPermissionName())).
                collect(Collectors.toList()));

        return userDTO;
    }
}
