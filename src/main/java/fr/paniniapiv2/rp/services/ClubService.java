package fr.paniniapiv2.rp.services;

import fr.paniniapiv2.rp.db.Club;
import fr.paniniapiv2.rp.repositories.ClubRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClubService {
    @Autowired
    ClubRepository clubRepository;

    public ClubService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    public Club getClubByName(String name) {
        return this.clubRepository.findByName(name);
    }

    public Club getClubById(int id) {
        return this.clubRepository.getById(id);
    }

    public List<Club> getAllClubsWithoutBinouzeFC() {
        return this.clubRepository.findAllButBinouzeFC();
    }
}
