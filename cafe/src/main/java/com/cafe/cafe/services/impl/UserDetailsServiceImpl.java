package com.cafe.cafe.services.impl;

import com.cafe.cafe.entities.Users;
import com.cafe.cafe.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var usuario = getByEmail(username);

        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        return User
                .withUsername(username)
                .password(usuario.getPassword())
                .roles(new String[]{usuario.getRole()})
                .build();
    }

    public Users getByEmail(String email){
        return usersRepository.findByEmail(email).orElse(null);
    }

}
