package com.ideabytes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ideabytes.binding.TransactionEntity;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
	TransactionEntity findByAppIdAndUserId(int appId, int userId);

	List<TransactionEntity> findByUserId(int userId);
}
