package fr.paniniapiv2.rp.services;

import fr.paniniapiv2.rp.db.Club;
import fr.paniniapiv2.rp.db.FootballPlayer;
import fr.paniniapiv2.rp.enums.EPosition;
import fr.paniniapiv2.rp.repositories.FootballPlayerRepository;
import fr.paniniapiv2.rp.resources.CreateFootballPlayerResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FootballPlayerService {
    @Autowired
    private FootballPlayerRepository footballPlayerRepository;

    public FootballPlayerService(FootballPlayerRepository footballPlayerRepository) {
        this.footballPlayerRepository = footballPlayerRepository;
    }

    public List<FootballPlayer> getAllFootballPlayers() {
        return this.footballPlayerRepository.findAll();
    }

    public List<FootballPlayer> getFootballPlayersByClub(Club club) {
        return this.footballPlayerRepository.findFootballPlayersByClub(club);
    }

    public FootballPlayer getFootballPlayerById(int id) {
        return this.footballPlayerRepository.findById(id).orElse(null);
    }

    public List<FootballPlayer> findFootballPlayersOfPlayerByDiscordId(String discordId) {
        return this.footballPlayerRepository.findFootballPlayersByOwnerDiscordId(discordId);
    }

    public FootballPlayer createFootballPlayer(CreateFootballPlayerResource createFootballPlayerResource) {
        FootballPlayer footballPlayer = new FootballPlayer();

        footballPlayer.setLevel(1);
        footballPlayer.setFirstName(createFootballPlayerResource.getFirstName());
        footballPlayer.setLastName(createFootballPlayerResource.getLastName());
        footballPlayer.setExperience(0);
        footballPlayer.setFavoriteClubs(new ArrayList<>());
        footballPlayer.setFavoritePersons(new ArrayList<>());
        footballPlayer.setPosition(EPosition.CENTER);

        return this.footballPlayerRepository.save(footballPlayer);
    }
}
