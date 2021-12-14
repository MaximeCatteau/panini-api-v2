package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.LogoLadder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LogoLadderRepository extends JpaRepository<LogoLadder,Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM logo_ladder WHERE league = 1 order by total_points DESC")
    List<LogoLadder> getLeague1Ladder();

    @Query(nativeQuery = true, value = "SELECT * FROM logo_ladder WHERE league = 2 order by total_points DESC")
    List<LogoLadder> getLeague2Ladder();

    @Query(nativeQuery = true, value = "select * from logo_ladder where logo_player_id = :logoPlayerId")
    LogoLadder findByLogoPlayerId(@Param(value = "logoPlayerId") int logoPlayerId);
}
