package com.ideabytes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ideabytes.binding.CurrentActionEntity;

@Repository
public interface CurrentActionRepository extends JpaRepository<CurrentActionEntity, Integer> {
	public List<CurrentActionEntity> findByUserId(int id);

	public CurrentActionEntity findByUserIdAndAppId(int userId, int appId);

	int deleteByUserIdAndAppId(int userId, int appId);
}