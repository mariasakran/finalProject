package com.example.demo.category;


import com.example.demo.Surveys.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Category addCategory(Category category) {
        return repository.save(category);
    }

    public Category markIsAccepted(Long categoryId){
        Category category = repository.findById(categoryId)
                .orElseThrow(()->new RuntimeException("category not found"));

        category.setAccepted(true);
        return repository.save(category);
    }

    public List<Category> getAllAcceptedCategories() {
    return repository.findByIsAcceptedTrue();
    }

    public List<Category> getAllPendingCategories() {
    return repository.findByIsAcceptedFalse();
    }

    public List<Category> getAllCategories() {
        return repository.findAll();
    }

    public void deleteCategoryById(Long id) {
        if (!repository.existsById(id))
            throw new RuntimeException("category not found");
        repository.deleteById(id);

    }
}
