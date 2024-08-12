package com.cafe.cafe.repositories;

import com.cafe.cafe.entities.Users;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends CrudRepository<Users, Long>, QueryByExampleExecutor<Users> {

    Optional<Users> findByEmail(String email);
}
