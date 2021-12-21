package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TitleRepository extends JpaRepository<Title, Integer> {
    @Query(nativeQuery = true, value = "select * from title where label = :label")
    Title findByLabel(@Param("label") String label);
}
