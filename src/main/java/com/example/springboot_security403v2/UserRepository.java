package com.example.springboot_security403v2;

import org.springframework.data.repository.CrudRepository;
// CRUD - create, read, update, delete
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);

}
