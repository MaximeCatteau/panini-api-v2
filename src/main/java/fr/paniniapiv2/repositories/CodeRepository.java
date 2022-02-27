package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Code;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CodeRepository extends JpaRepository<Code, Long> {
    Boolean existsByValue(String value);

    Boolean existsByValueAndPlayerAssociated(String value, Long playerAssociated);

    Code findByValue(String value);

    @Query(nativeQuery = true, value = "SELECT c.value from codes c LEFT JOIN players p ON p.id = c.player_associated WHERE p.discord_id = :discordId")
    List<String> getCodesForPlayerByDiscordId(String discordId);
}
