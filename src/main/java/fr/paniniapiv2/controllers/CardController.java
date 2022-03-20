package fr.paniniapiv2.controllers;

import fr.paniniapiv2.db.Card;
import fr.paniniapiv2.repositories.CardRepository;
import fr.paniniapiv2.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CardController {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    PlayerRepository playerRepository;

    @CrossOrigin
    @GetMapping("/cards")
    public List<Card> getAllCards() {
        return this.cardRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/cards/card")
    public Card getCardByCardId(@RequestParam int cardId) {
        return this.cardRepository.findById(cardId);
    }

    @CrossOrigin
    @GetMapping("/cards/randoms")
    public List<Card> getSomeRandomCards(@RequestParam int nb) {
        return this.cardRepository.getSomeRandomCards(nb);
    }

    @CrossOrigin
    @GetMapping("/cards/collection")
    public List<Card> getCardsByCollectionId(@RequestParam int collectionId) {
        return this.cardRepository.findByCollectionId(collectionId);
    }

    @CrossOrigin
    @GetMapping("/cards/collection/length")
    public Integer getCollectionLength(@RequestParam int collectionId) {
        return this.cardRepository.countByCollectionId(collectionId);
    }
}
