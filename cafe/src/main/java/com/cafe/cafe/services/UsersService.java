package com.cafe.cafe.services;

import com.cafe.cafe.commons.GenericServiceAPI;
import com.cafe.cafe.entities.Users;

import java.util.Optional;

public interface UsersService extends GenericServiceAPI<Users, Long> {

    Optional<Users> getByEmail(String email);

    Iterable<Users> getUsers();

    Users deleteUser(Long id);

}
