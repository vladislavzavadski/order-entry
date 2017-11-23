package com.netcracker.orderentry.inventory.service.impl;

import com.netcracker.orderentry.inventory.client.AuthenticationClient;
import com.netcracker.orderentry.inventory.domain.to.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DefaultUserDetailsService implements UserDetailsService {

    private final AuthenticationClient authenticationClient;

    @Autowired
    public DefaultUserDetailsService(AuthenticationClient authenticationClient) {
        this.authenticationClient = authenticationClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = authenticationClient.getUserByUsername(username);

        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        List<GrantedAuthority> grantedAuthoritySet = user.getPermissionDTOList().stream().map(e -> new SimpleGrantedAuthority(e.getPermissionName())).collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthoritySet);

    }

}
