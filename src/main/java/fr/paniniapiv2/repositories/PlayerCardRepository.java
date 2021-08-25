package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.PlayerCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;


public interface PlayerCardRepository extends JpaRepository<PlayerCard, Integer> {
    Boolean existsByCardIdAndPlayerId(int cardId, Long playerId);

    Optional<PlayerCard> findByCardIdAndPlayerId(int cardId, Long playerId);

    @Query(nativeQuery = true, value = "select count(*) from player_card pc inner join card c on pc.card_id = c.id where player_id = :playerId and c.collection_id = :collectionId")
    Integer getNumberOfPlayerCardsOnCollection(@Param("playerId") Long playerId, @Param("collectionId") int collectionId);

    @Query(nativeQuery = true, value = "select * from player_card pc where player_id = :playerId and quantity > 1;")
    List<PlayerCard> getPlayerDoubles(@Param("playerId") Long playerId);
}
