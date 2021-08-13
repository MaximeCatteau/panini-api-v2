package fr.paniniapiv2.repositories;

import fr.paniniapiv2.db.Code;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, Long> {
    Boolean existsByValue(String value);

    Boolean existsByValueAndPlayerAssociated(String value, Long playerAssociated);

    Code findByValue(String value);
}
