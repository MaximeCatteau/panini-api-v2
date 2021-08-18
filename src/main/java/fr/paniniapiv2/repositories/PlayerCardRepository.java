package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.PlayerCard;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface PlayerCardRepository extends JpaRepository<PlayerCard, Integer> {
    Boolean existsByCardIdAndPlayerId(int cardId, Long playerId);

    Optional<PlayerCard> findByCardIdAndPlayerId(int cardId, Long playerId);
}
