package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.LogoSeason;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LogoSeasonRepository extends JpaRepository<LogoSeason, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM logo_season WHERE status = 'IN_PROGRESS'")
    LogoSeason findCurrentSeason();
}
