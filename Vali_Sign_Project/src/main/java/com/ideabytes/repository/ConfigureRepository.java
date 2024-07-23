package com.ideabytes.repository;

import org.springframework.data.repository.CrudRepository;

import com.ideabytes.binding.ConfigurationEntity;

public interface ConfigureRepository extends CrudRepository<ConfigurationEntity, Integer> {
	ConfigurationEntity findByCkey(String ckey);
	ConfigurationEntity findById(int id);
	int deleteById(long id);
}
