package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.PlayerTitle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerTitleRepository extends JpaRepository<PlayerTitle, Integer> {
    @Query(nativeQuery = true, value = "select * from player_title pt where player_id = :playerId and selected is true;")
    PlayerTitle getSelectedTitleByPlayerId(@Param("playerId") Long playerId);

    @Query(nativeQuery = true, value = "select * from player_title where logo_player_id = :logoPlayerId and selected is true;")
    PlayerTitle getSelectedTitleByLogoPlayerId(@Param("logoPlayerId") int logoPlayerId);

    @Query(nativeQuery = true, value = "select * from player_title where player_id = :playerId")
    List<PlayerTitle> getAllPlayerTitlesByPlayerId(@Param("playerId") Long playerId);

    @Query(nativeQuery = true, value = "select * from player_title pt where player_id = :playerId and title_id = :titleId")
    PlayerTitle findByPlayerIdAndTitleId(@Param("playerId") Long playerId, @Param("titleId") int titleId);
}
