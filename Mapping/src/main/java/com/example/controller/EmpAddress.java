package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.binding.Employee;
import com.example.repository.EmployeeRepo;

@RestController
public class EmpAddress {
	@Autowired
	public EmployeeRepo repo;
	
	@PostMapping("/emp")
	public ResponseEntity<String>employeeDataSave(@RequestBody List<Employee> empData) {
		
		repo.saveAll(empData);
		
		return ResponseEntity.ok("Data Saved");
		
	}

}
