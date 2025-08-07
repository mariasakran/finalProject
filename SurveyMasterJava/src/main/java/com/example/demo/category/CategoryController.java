package com.example.demo.category;


import com.example.demo.Surveys.Survey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;
    private CategoryRepository categoryRepository;

    @PostMapping
    public Category addCategory(@RequestBody Category category) {
        return service.addCategory(category);
    }

    @GetMapping
    public List<Category> getAllCategories() {
        return service.getAllCategories();
    }

    @GetMapping("/getUnAcceptedCategory")
    public ResponseEntity<List<Category>>getUnAcceptedCategory(){
        List<Category> categories = service.getAllPendingCategories();
        return ResponseEntity.ok(categories);
    }

    @GetMapping("/getAcceptedCategory")
    public ResponseEntity<List<Category>>getAcceptedCategory(){
        List<Category> categories = service.getAllAcceptedCategories();
        return ResponseEntity.ok(categories);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Category> deleteCategory(@PathVariable Long id) {
        service.deleteCategoryById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/IsAccepted/{categoryId}")
    public ResponseEntity<Category> markIsAccepted(@PathVariable Long categoryId){
        Category category = service.markIsAccepted(categoryId);
        return ResponseEntity.ok(category);

    }
}