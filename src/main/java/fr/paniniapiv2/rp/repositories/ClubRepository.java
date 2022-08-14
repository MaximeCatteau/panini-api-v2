package fr.paniniapiv2.rp.repositories;

import fr.paniniapiv2.rp.db.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClubRepository extends JpaRepository<Club,Integer> {
    Club findByName(String name);

    @Query(nativeQuery = true, value = "SELECT * FROM club WHERE id <> 1")
    List<Club> findAllButBinouzeFC();
}
