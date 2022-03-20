package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.BanWords;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BanWordsRepository extends JpaRepository<BanWords, Long> {
    Boolean existsByWord(String word);

    BanWords findByWord(String word);
}
