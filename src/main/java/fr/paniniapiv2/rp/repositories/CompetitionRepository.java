package fr.paniniapiv2.rp.repositories;

import fr.paniniapiv2.rp.db.Competition;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompetitionRepository extends JpaRepository<Competition, Integer> {
}
