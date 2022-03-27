package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByUsername(String username);

    Optional<Player> findByToken(String token);

    Boolean existsByUsername(String username);

    Boolean existsByDiscordId(String discordId);

    Optional<Player> findByDiscordId(String discordId);
}
