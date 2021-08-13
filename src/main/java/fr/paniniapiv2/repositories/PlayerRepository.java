package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByUsername(String username);

    Boolean existsByUsername(String username);
}
