package com.cafe.cafe.services.impl;

import com.cafe.cafe.commons.GenericServiceImpl;
import com.cafe.cafe.entities.Users;
import com.cafe.cafe.repositories.UsersRepository;
import com.cafe.cafe.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersServiceImpl extends GenericServiceImpl<Users, Long> implements UsersService {

    @Autowired
    private UsersRepository usersRepository;

    @Override
    public CrudRepository<Users, Long> getDao() {
        return usersRepository;
    }

    @Override
    public Optional<Users> getByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    @Override
    public Iterable<Users> getUsers() {
        return usersRepository.findAll();
    }

    @Override
    public Users deleteUser(Long id) {
        Optional<Users> users = usersRepository.findById(id);
        if(users.isPresent()){
            Users usersUpdate = users.get();
            usersUpdate.setStatus(0);
            return usersRepository.save(usersUpdate);
        }
        return null;
    }
}
