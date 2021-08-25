package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.PlayerCollection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayerCollectionRepository extends JpaRepository<PlayerCollection, Integer> {
    @Query(nativeQuery = true, value = "select count(*) from player_collection pc inner join collection co on pc.collection_id = co.id inner join card ca on ca.collection_id = co.id where pc.player_id = :playerId")
    int getTotalNumberOfCardsInPlayerCollection(@Param("playerId") Long playerId);
}
