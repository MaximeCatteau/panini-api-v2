package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CardRepository extends JpaRepository<Card, Long> {
    Card findById(int id);

    @Query(nativeQuery = true, value = "SELECT * FROM card ORDER BY RANDOM() LIMIT 1")
    Card findRandomCard();

    @Query(nativeQuery = true, value = "SELECT * FROM card ORDER BY RANDOM() LIMIT :nb")
    List<Card> getSomeRandomCards(int nb);

    @Query(nativeQuery = true, value = "SELECT * FROM card WHERE collection_id = :collectionId ORDER BY id_in_collection ASC")
    List<Card> findByCollectionId(int collectionId);

    @Query(nativeQuery = true, value = "select * from card c where id in (select card_id from player_card pc where pc.player_id = :playerId)")
    List<Card> getAllPlayerCards(Long playerId);

    @Query(nativeQuery = true, value = "select * from card c where collection_id = :collectionId and id in (select card_id from player_card pc where pc.player_id = :playerId)")
    List<Card> getPlayerCardsByCollectionId(@Param(value = "playerId") Long playerId, @Param(value = "collectionId") int collectionId);

    Integer countByCollectionId(int collectionId);
}
