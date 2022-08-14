package fr.paniniapiv2.rp.controllers;

import fr.paniniapiv2.rp.db.Club;
import fr.paniniapiv2.rp.db.Match;
import fr.paniniapiv2.rp.resources.CreateMatchResource;
import fr.paniniapiv2.rp.resources.MatchResource;
import fr.paniniapiv2.rp.resources.PlayMatchResource;
import fr.paniniapiv2.rp.services.ClubService;
import fr.paniniapiv2.rp.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MatchController {
    @Autowired
    MatchService matchService;

    @Autowired
    ClubService clubService;

    @CrossOrigin
    @GetMapping("rpbinouze/matchs")
    public ResponseEntity<List<MatchResource>> getAllMatchesByClub(@RequestParam int clubId) {
        List<MatchResource> matchResources = new ArrayList<>();

        Club club = this.clubService.getClubById(clubId);

        List<Match> matches = this.matchService.getAllMatchesForClub(club);

        for (Match m : matches) {
            MatchResource matchResource = new MatchResource();
            matchResources.add(matchResource.fromMatch(m));
        }

        return new ResponseEntity<>(matchResources, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("rpbinouze/match")
    public ResponseEntity<Match> getMatchById(@RequestParam int matchId) {
        Match match = this.matchService.getMatchById(matchId);

        return new ResponseEntity<>(match, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/rpbinouze/match/create")
    public ResponseEntity<Match> createMatch(@RequestBody CreateMatchResource createMatchResource) {
        Match m = new Match();

        Club home = this.clubService.getClubByName(createMatchResource.getHome());
        Club away = this.clubService.getClubByName(createMatchResource.getAway());

        m.setHome(home);
        m.setAway(away);
        m.setDate(createMatchResource.getDate());

        return new ResponseEntity<>(this.matchService.createMatch(m), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/rpbinouze/match/play")
    public ResponseEntity<MatchResource> playMatch(@RequestBody PlayMatchResource playMatchResource) throws IOException, URISyntaxException {
        return new ResponseEntity<>(MatchResource.fromMatch(this.matchService.playMatch(playMatchResource)), HttpStatus.OK);
    }
}
