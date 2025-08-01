package com.example.demo.category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface CategoryRepository extends JpaRepository <Category,Long> {

    List<Category> findByIsAcceptedTrue();
    List<Category> findByIsAcceptedFalse();

}
