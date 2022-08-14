package fr.paniniapiv2.rp.controllers;

import fr.paniniapiv2.rp.db.Club;
import fr.paniniapiv2.rp.db.Competition;
import fr.paniniapiv2.rp.services.ClubService;
import fr.paniniapiv2.rp.services.CompetitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CompetitionController {
    @Autowired
    CompetitionService competitionService;

    @CrossOrigin
    @GetMapping("/rpbinouze/competitions")
    public ResponseEntity<List<Competition>> getAllCompetitions() {
        List<Competition> competitions = this.competitionService.getAllCompetition();

        return new ResponseEntity<>(competitions, HttpStatus.OK);
    }
}
