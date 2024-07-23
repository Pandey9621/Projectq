package com.ideabytes.repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

import org.json.JSONArray;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.ideabytes.binding.UserBookEntity;
@Repository
public interface UserBookRepository extends JpaRepository<UserBookEntity, Integer>{

	List<UserBookEntity> findByUserid(int uid);
	UserBookEntity findByBookid(Integer integer);



}