package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findById(int id);

    @Query(nativeQuery = true, value = "select * from card where card_rarity = :rarity and collection_id in (select collection_id from player_collection pc where pc.player_id = :playerId) order by random() limit 1")
    Card findRandomCard(@Param(value = "playerId") Long playerId, @Param(value = "rarity") String rarity);

    @Query(nativeQuery = true, value = "SELECT * FROM card ORDER BY RANDOM() LIMIT :nb")
    List<Card> getSomeRandomCards(@Param(value = "nb") int nb);

    @Query(nativeQuery = true, value = "SELECT * FROM card WHERE collection_id = :collectionId ORDER BY id_in_collection ASC")
    List<Card> findByCollectionId(@Param(value = "collectionId") int collectionId);

    @Query(nativeQuery = true, value = "select * from card c where id in (select card_id from player_card pc where pc.player_id = :playerId) order by c.id ASC")
    List<Card> getAllPlayerCards(@Param(value = "playerId") Long playerId);

    @Query(nativeQuery = true, value = "select * from card c where collection_id = :collectionId and id in (select card_id from player_card pc where pc.player_id = :playerId)")
    List<Card> getPlayerCardsByCollectionId(@Param(value = "playerId") Long playerId, @Param(value = "collectionId") int collectionId);

    Integer countByCollectionId(int collectionId);

    @Query(nativeQuery = true, value = "select c.* from card c left join player_card pc on pc.card_id = c.id where pc.quantity > 1 and pc.player_id = :playerId")
    List<Card> getDoubleCardsForPlayer(@Param(value = "playerId") Long playerId);
}
