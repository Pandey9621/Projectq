/*********************** Ideabytes Software India Pvt Ltd *********************                                   
 
* Here,This is a UserRepository interfaceis extending CrudRepository for performing CRUD Operation.
* *@author  Chandan Pandey
* @version 20.0.1
* @since   2023-06-23.
*/

package com.ideabytes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideabytes.binding.UserEntity;


//@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer>{
	public UserEntity findByUserId(String userId);

	public UserEntity findByName(String name);
	

	
	

}
