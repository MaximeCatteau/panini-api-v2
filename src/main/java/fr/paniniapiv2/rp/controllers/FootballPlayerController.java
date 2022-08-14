package fr.paniniapiv2.rp.controllers;

import fr.paniniapiv2.rp.db.Club;
import fr.paniniapiv2.rp.db.FootballPlayer;
import fr.paniniapiv2.rp.resources.CreateFootballPlayerResource;
import fr.paniniapiv2.rp.resources.FootballPlayerAdminResource;
import fr.paniniapiv2.rp.resources.FootballPlayerCardResource;
import fr.paniniapiv2.rp.resources.FootballPlayerProfileResource;
import fr.paniniapiv2.rp.services.ClubService;
import fr.paniniapiv2.rp.services.FootballPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class FootballPlayerController {
    @Autowired
    FootballPlayerService footballPlayerService;

    @Autowired
    ClubService clubService;

    @CrossOrigin
    @GetMapping("/rpbinouze/players")
    public ResponseEntity<List<FootballPlayer>> getFootballPlayers() {
        List<FootballPlayer> footballPlayers = this.footballPlayerService.getAllFootballPlayers();

        return new ResponseEntity<>(footballPlayers, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/rpbinouze/players/club")
    public ResponseEntity<List<FootballPlayerCardResource>> getFootballPlayersByClub(@RequestParam String clubName) throws ParseException {
        Club club = this.clubService.getClubByName(clubName);
        List<FootballPlayer> footballPlayers = this.footballPlayerService.getFootballPlayersByClub(club);
        List<FootballPlayerCardResource> resources = new ArrayList<>();

        for(FootballPlayer fp : footballPlayers) {
            FootballPlayerCardResource fpr = new FootballPlayerCardResource();
            resources.add(fpr.fromFootballPlayer(fp));
        }

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/rpbinouze/admin/players")
    public ResponseEntity<List<FootballPlayerAdminResource>> getFootballPlayersAdmin() {
        List<FootballPlayer> footballPlayers = this.footballPlayerService.getAllFootballPlayers();
        List<FootballPlayerAdminResource> resources = new ArrayList<>();

        for (FootballPlayer fp : footballPlayers) {
            FootballPlayerAdminResource fpar = new FootballPlayerAdminResource();
            resources.add(fpar.fromFootballPlayer(fp));
        }

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/rpbinouze/player")
    public ResponseEntity<FootballPlayerProfileResource> getFootballPlayerProfileById(@RequestParam int id) {
        FootballPlayer footballPlayer = this.footballPlayerService.getFootballPlayerById(id);
        FootballPlayerProfileResource footballPlayerProfileResource = new FootballPlayerProfileResource();

        return new ResponseEntity<>(footballPlayerProfileResource.fromFootballPlayer(footballPlayer), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/rpbinouze/players/create")
    public ResponseEntity<FootballPlayer> createFootballPlayer(CreateFootballPlayerResource footballPlayerResource) {
        return new ResponseEntity<>(this.footballPlayerService.createFootballPlayer(footballPlayerResource), HttpStatus.OK);
    }
}
