package com.ideabytes.repository;

import org.springframework.data.repository.CrudRepository;

import com.ideabytes.binding.UserEntity2;

public interface UserRepository2 extends CrudRepository<UserEntity2, Integer>
{
	public UserEntity2 findByUserId(String id);

	public UserEntity2 findByNameAndPassword(String name, String password);

	public UserEntity2 findByEmailOrPhone(String email, String phone);

	public UserEntity2 deleteByUserId(String userId);
	
	public UserEntity2 findByEmailAndPassword(String email, String password);
	public UserEntity2 findByEmail(String email);


}
