package fr.paniniapiv2.controllers;

import fr.paniniapiv2.PlayerResource;
import fr.paniniapiv2.db.Player;
import fr.paniniapiv2.db.PlayerCareer;
import fr.paniniapiv2.repositories.PlayerCareerRepository;
import fr.paniniapiv2.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PlayerCareerController {

    @Autowired
    PlayerCareerRepository playerCareerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @PostMapping("/career")
    public PlayerCareer getPlayerCareer(@RequestBody PlayerResource playerResource) {
        System.out.println("### CALLED");
        Player player = this.playerRepository.findByUsername(playerResource.getUsername()).orElseThrow();

        PlayerCareer playerCareer = this.playerCareerRepository.findByPlayerId(player.getId());

        return playerCareer;
    }
}
