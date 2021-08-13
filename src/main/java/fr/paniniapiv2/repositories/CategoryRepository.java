package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
