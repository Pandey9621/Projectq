package com.ideabytes.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import com.ideabytes.binding.BookEntity;

public interface BookRepository extends CrudRepository<BookEntity, Integer> {

}
