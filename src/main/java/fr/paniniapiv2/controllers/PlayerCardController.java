package fr.paniniapiv2.controllers;

import fr.paniniapiv2.PlayerResource;
import fr.paniniapiv2.db.Card;
import fr.paniniapiv2.db.Player;
import fr.paniniapiv2.repositories.CardRepository;
import fr.paniniapiv2.repositories.PlayerCardRepository;
import fr.paniniapiv2.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PlayerCardController {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    PlayerCardRepository playerCardRepository;
    @Autowired
    CardRepository cardRepository;

    @GetMapping("/player/cards")
    public ResponseEntity<List<Card>> getAllPlayerCards(@RequestBody PlayerResource playerResource) {
        Player player = playerRepository.findByUsername(playerResource.getUsername()).get();

        List<Card> playerCards = this.cardRepository.getAllPlayerCards(player.getId());

        return new ResponseEntity<>(playerCards, HttpStatus.OK);
    }

    @PostMapping("/player/cards/collection")
    public ResponseEntity<List<Card>> getPlayerCardsByCollectionId(@RequestBody PlayerResource playerResource, @RequestParam int collectionId) {
        Player player = playerRepository.findByUsername(playerResource.getUsername()).get();

        List<Card> playerCards = this.cardRepository.getPlayerCardsByCollectionId(player.getId(), collectionId);

        return new ResponseEntity<>(playerCards, HttpStatus.OK);
    }
}
