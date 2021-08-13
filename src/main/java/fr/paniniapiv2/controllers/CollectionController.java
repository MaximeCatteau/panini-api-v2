package fr.paniniapiv2.controllers;

import fr.paniniapiv2.db.Collection;
import fr.paniniapiv2.repositories.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CollectionController {
    @Autowired
    CollectionRepository collectionRepository;

    @GetMapping("/collections/category")
    public List<Collection> getCollectionByCategoryId(@RequestParam int categoryId) {
        return this.collectionRepository.findByCategoryId(categoryId);
    }
}
