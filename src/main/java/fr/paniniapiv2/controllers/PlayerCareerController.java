package fr.paniniapiv2.controllers;

import fr.paniniapiv2.db.Collection;
import fr.paniniapiv2.db.Player;
import fr.paniniapiv2.db.PlayerCareer;
import fr.paniniapiv2.repositories.*;
import fr.paniniapiv2.resources.PlayerCareerResource;
import fr.paniniapiv2.resources.PlayerProgressOnCollectionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class PlayerCareerController {

    @Autowired
    PlayerCareerRepository playerCareerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    PlayerCardRepository playerCardRepository;

    @CrossOrigin
    @GetMapping("/career")
    public ResponseEntity<PlayerCareerResource> getPlayerCareer(@RequestParam String token) {
        Player player = this.playerRepository.findByToken(token).orElseThrow();
        PlayerCareer playerCareer = this.playerCareerRepository.findByPlayerId(player.getId());

        PlayerCareerResource playerCareerResource = new PlayerCareerResource();

        playerCareerResource.setPlayerId(player.getId());
        playerCareerResource.setTotalCards(playerCareer.getTotalCard());
        playerCareerResource.setTotalCardsToGet(playerCareer.getTotalCard() + playerCareer.getMissingCards());
        playerCareerResource.setCollectionsCompleted(playerCareer.getCollectionsCompleted());
        playerCareerResource.setTradesCompleted(playerCareer.getTradeCompleted());
        playerCareerResource.setMoneySpent(playerCareer.getCashSpent());

        return new ResponseEntity<>(playerCareerResource, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/career/collections")
    public ResponseEntity<List<PlayerProgressOnCollectionResource>> getPlayerProgressionOnEachCollection(@RequestParam String token) {
        Player player = this.playerRepository.findByToken(token).orElseThrow();
        List<PlayerProgressOnCollectionResource> resource = new ArrayList<>();

        List<Collection> playerCollections = this.collectionRepository.getCollectionsOwnedByPlayer(player.getId());

        for(Collection c : playerCollections) {
            PlayerProgressOnCollectionResource ppocr = new PlayerProgressOnCollectionResource();

            ppocr.setCollectionLabel(c.getName());
            ppocr.setCollectionCardCount(this.cardRepository.countByCollectionId(c.getId()));
            ppocr.setPlayerCardCount(this.playerCardRepository.getNumberOfPlayerCardsOnCollection(player.getId(), c.getId()));
            ppocr.setPlayerCardCountPercent((int) Math.ceil(ppocr.getPlayerCardCount() * 100 / ppocr.getCollectionCardCount()));

            resource.add(ppocr);
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }
}
