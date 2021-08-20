package fr.paniniapiv2.controllers;

import fr.paniniapiv2.PlayerResource;
import fr.paniniapiv2.db.Card;
import fr.paniniapiv2.db.Player;
import fr.paniniapiv2.repositories.CardRepository;
import fr.paniniapiv2.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CardController {
    @Autowired
    CardRepository cardRepository;

    @Autowired
    PlayerRepository playerRepository;

    @GetMapping("/cards")
    public List<Card> getAllCards() {
        return this.cardRepository.findAll();
    }

    @GetMapping("/cards/card")
    public Card getCardByCardId(@RequestParam int cardId) {
        return this.cardRepository.findById(cardId);
    }

    @GetMapping("/cards/randoms")
    public List<Card> getSomeRandomCards(@RequestParam int nb) {
        return this.cardRepository.getSomeRandomCards(nb);
    }

    @GetMapping("/cards/collection")
    public List<Card> getCardsByCollectionId(@RequestParam int collectionId) {
        return this.cardRepository.findByCollectionId(collectionId);
    }

    @GetMapping("/cards/collection/length")
    public Integer getCollectionLength(@RequestParam int collectionId) {
        return this.cardRepository.countByCollectionId(collectionId);
    }
}
