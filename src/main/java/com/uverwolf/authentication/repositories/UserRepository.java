package com.uverwolf.authentication.repositories;

import org.springframework.data.repository.CrudRepository;

import com.uverwolf.authentication.models.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByEmail(String email);
}
