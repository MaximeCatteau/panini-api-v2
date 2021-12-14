package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.LogoDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogoDayRepository extends JpaRepository<LogoDay,Integer> {
    @Query(nativeQuery = true, value = "select * from logo_day WHERE season_id = :seasonId and day = :day")
    List<LogoDay> findBySeasonIdAndDay(@Param(value = "seasonId") int seasonId, @Param(value = "day") String day);
}
