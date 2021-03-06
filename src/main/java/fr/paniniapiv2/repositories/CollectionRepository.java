package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM collection WHERE category_id = :categoryId")
    List<Collection> findByCategoryId(int categoryId);
}
