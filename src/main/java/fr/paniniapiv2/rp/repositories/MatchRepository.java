package fr.paniniapiv2.rp.repositories;

import fr.paniniapiv2.rp.db.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match,Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM match m WHERE m.home_id = :clubId OR m.away_id = :clubId")
    List<Match> getAllMatchByClub(int clubId);
}
