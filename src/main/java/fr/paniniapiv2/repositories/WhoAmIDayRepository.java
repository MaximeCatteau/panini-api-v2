package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.WhoAmIDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface WhoAmIDayRepository extends JpaRepository<WhoAmIDay, Integer> {
    @Query(nativeQuery = true, value = "select * from who_amiday WHERE season_id = :seasonId and day = :day")
    List<WhoAmIDay> findBySeasonIdAndDay(@Param(value = "seasonId") Integer seasonId, @Param(value = "day") int day);
}
