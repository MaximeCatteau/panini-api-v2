package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.WhoAmISeasonPlayer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WhoAmISeasonPlayerRepository extends JpaRepository<WhoAmISeasonPlayer, Integer> {
    Integer countByWhoAmISeasonId(Integer whoAmISeasonId);

    WhoAmISeasonPlayer findByWhoAmIPlayerId(Integer whoAmIPlayerId);

    Boolean existsByWhoAmIPlayerId(Integer whoAmIPlayerId);
}
