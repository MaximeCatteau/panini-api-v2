package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.LogoPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LogoPlayerRepository extends JpaRepository<LogoPlayer,Integer> {
    @Query(nativeQuery = true, value = "select * from logo_player where id = :id")
    LogoPlayer findById(int id);

    @Query(nativeQuery = true, value = "select * from logo_player where logo_player_name = :name")
    LogoPlayer findByPlayerName(@Param(value = "name") String name);
}
