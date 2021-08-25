package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Ladder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LadderRepository extends JpaRepository<Ladder, Integer> {
    @Query(nativeQuery = true, value = "SELECT * FROM ladder ORDER BY card_count DESC")
    List<Ladder> getLadder();

    Ladder findByPlayerUsername(String playerUsername);
}
