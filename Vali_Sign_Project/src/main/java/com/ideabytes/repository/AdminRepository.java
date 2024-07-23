package com.ideabytes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ideabytes.binding.AdminEntity;

@Repository
public interface AdminRepository extends JpaRepository<AdminEntity, Integer> {
	AdminEntity findByEmail(String email);

	AdminEntity findById(int id);

	AdminEntity findByEmailAndPassword(String email, String password);

	int deleteById(long id);
	int deleteByEmail(String email);
	
	

}
