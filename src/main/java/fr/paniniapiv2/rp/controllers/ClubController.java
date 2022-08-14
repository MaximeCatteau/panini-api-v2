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
public class ClubController {
    @Autowired
    ClubService clubService;

    @CrossOrigin
    @GetMapping("/rpbinouze/clubs/allBut")
    public ResponseEntity<List<Club>> getAllClubsButBinouzeFC() {
        List<Club> clubs = this.clubService.getAllClubsWithoutBinouzeFC();

        return new ResponseEntity<>(clubs, HttpStatus.OK);
    }
}
