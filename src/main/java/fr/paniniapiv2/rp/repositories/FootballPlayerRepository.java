package fr.paniniapiv2.rp.repositories;

import fr.paniniapiv2.rp.db.Club;
import fr.paniniapiv2.rp.db.FootballPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FootballPlayerRepository extends JpaRepository<FootballPlayer,Integer>  {
    List<FootballPlayer> findFootballPlayersByClub(Club club);

    List<FootballPlayer> findFootballPlayersByOwnerDiscordId(String ownerDiscordId);
}
