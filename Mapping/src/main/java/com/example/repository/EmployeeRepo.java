package com.example.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.binding.Employee;

@Repository
public interface EmployeeRepo extends JpaRepository<Employee, Integer>{

//	public List saveAll(List<Employee> empData);

}
