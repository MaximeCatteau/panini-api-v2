package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.WhoAmILadder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WhoAmILadderRepository extends JpaRepository<WhoAmILadder, Integer> {
    List<WhoAmILadder> findBySeasonId(Integer seasonId);

    WhoAmILadder findByWhoAmIPlayerId(Integer whoAmIPlayerId);

    WhoAmILadder findByWhoAmIPlayerIdAndSeasonId(Integer whoAmIPlayerId, Integer seasonId);
}
