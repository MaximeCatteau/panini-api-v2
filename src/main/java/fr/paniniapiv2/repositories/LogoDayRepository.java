package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.LogoDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LogoDayRepository extends JpaRepository<LogoDay,Integer> {
    @Query(nativeQuery = true, value = "select * from logo_day WHERE season_id = :seasonId and day = :day")
    List<LogoDay> findBySeasonIdAndDay(int seasonId, String day);
}
