package fr.paniniapiv2.controllers;

import fr.paniniapiv2.db.Category;
import fr.paniniapiv2.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CategoryController {
    @Autowired
    CategoryRepository categoryRepository;

    @CrossOrigin
    @GetMapping("/categories")
    public List<Category> getAllCategories(){
        return this.categoryRepository.findAll();
    }
}
