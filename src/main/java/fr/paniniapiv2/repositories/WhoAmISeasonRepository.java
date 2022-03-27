package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.WhoAmISeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WhoAmISeasonRepository extends JpaRepository<WhoAmISeason, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM who_amiseason WHERE status = 'IN_PROGRESS'")
    WhoAmISeason findCurrentSeason();
}
