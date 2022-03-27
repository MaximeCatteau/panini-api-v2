package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.WhoAmIPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhoAmIPlayerRepository extends JpaRepository<WhoAmIPlayer, Integer> {
    WhoAmIPlayer findWhoAmIPlayerByDiscordId(String discordId);
}
