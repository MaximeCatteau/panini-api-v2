package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.PlayerCareer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerCareerRepository extends JpaRepository<PlayerCareer, Integer> {
    PlayerCareer findByPlayerId(Long playerId);
}
