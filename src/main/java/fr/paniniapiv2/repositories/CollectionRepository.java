package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Collection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

public interface CollectionRepository extends JpaRepository<Collection, Integer> {
    List<Collection> findByCategoryId(int categoryId);

    @Query(nativeQuery = true, value = "SELECT * FROM collection WHERE price = 0")
    List<Collection> getFreeCollections();

    @Query(nativeQuery = true, value = "SELECT * FROM collection WHERE price > 0")
    List<Collection> getCollectionsToPay();

    @Query(nativeQuery = true, value = "SELECT * FROM collection WHERE category_id = :categoryId AND id in (SELECT collection_id FROM player_collection WHERE player_id = :playerId)")
    List<Collection> getCollectionsOwnedByPlayerByCategory(@Param("playerId") Long playerId, @Param("categoryId") int categoryId);

    @Query(nativeQuery = true, value = "SELECT * FROM collection WHERE id in (SELECT collection_id FROM player_collection WHERE player_id = :playerId) order by name ASC")
    List<Collection> getCollectionsOwnedByPlayer(@Param("playerId") Long playerId);

    @Query(nativeQuery = true, value = "select * from collection c where price > 0 and id in (select collection_id from player_collection pc where pc.player_id = :playerId)")
    List<Collection> getCollectionsAlreadyPaidByPlayer(@Param("playerId") Long playerId);

    @Query(nativeQuery = true, value = "select * from collection c where price > 0 and id not in (select collection_id from player_collection pc where pc.player_id = :playerId)")
    List<Collection> getCollectionsNotAlreadyPaidByPlayer(@Param("playerId") Long playerId);

    @Query(nativeQuery = true, value = "select count(*), pc.player_id from player_card pc inner join card c on c.id = pc.card_id where c.collection_id = :collectionId group by pc.player_id;")
    List<Object[]> getCardCountsByCollection(@Param("collectionId") int collectionId);

    @Query(nativeQuery = true, value = "select count(*), pc.player_id from player_card pc inner join card c on c.id = pc.card_id group by pc.player_id;")
    List<Object[]> getCardCountsForLadder();
}
