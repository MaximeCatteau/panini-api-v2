package fr.paniniapiv2.controllers;

import fr.paniniapiv2.PlayerResource;
import fr.paniniapiv2.db.Card;
import fr.paniniapiv2.db.Player;
import fr.paniniapiv2.db.PlayerCard;
import fr.paniniapiv2.db.Trade;
import fr.paniniapiv2.repositories.*;
import fr.paniniapiv2.resources.PlayerCardCollectionResource;
import fr.paniniapiv2.resources.PlayerCardResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class PlayerCardController {
    @Autowired
    PlayerRepository playerRepository;
    @Autowired
    PlayerCardRepository playerCardRepository;
    @Autowired
    CardRepository cardRepository;
    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    CollectionRepository collectionRepository;

    @CrossOrigin
    @GetMapping("/player/cards")
    public ResponseEntity<List<PlayerCardResource>> getAllPlayerCards(@RequestParam String token) {
        Player player = playerRepository.findByToken(token).get();
        List<PlayerCardResource> resources = new ArrayList<>();

        List<Card> playerCards = this.cardRepository.getAllPlayerCards(player.getId());

        for(Card c : playerCards) {
            PlayerCardResource pcr = new PlayerCardResource();
            pcr.setCard(c);
            PlayerCard playerCard = this.playerCardRepository.getPlayerCard(player.getId(), c.getId());
            pcr.setCardQuantity(playerCard.getQuantity());

            resources.add(pcr);
        }

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/player/cards/collection")
    public ResponseEntity<List<PlayerCardResource>> getPlayerCardsByCollectionId(@RequestParam String token, @RequestParam int collectionId) {
        Player player = playerRepository.findByToken(token).get();
        List<PlayerCardResource> resources = new ArrayList<>();

        List<Card> playerCards = this.cardRepository.getPlayerCardsByCollectionId(player.getId(), collectionId);

        for(Card c : playerCards) {
            PlayerCardResource pcr = new PlayerCardResource();
            pcr.setCard(c);
            PlayerCard playerCard = this.playerCardRepository.getPlayerCard(player.getId(), c.getId());
            pcr.setCardQuantity(playerCard.getQuantity());

            resources.add(pcr);
        }

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/player/cards/doubles")
    public ResponseEntity<List<PlayerCardCollectionResource>> getPlayerDoubleCards(@RequestParam String token) {
        Player player = playerRepository.findByToken(token).orElseThrow();

        List<Card> doubles = this.cardRepository.getDoubleCardsForPlayer(player.getId());
        List<PlayerCardCollectionResource> resources = new ArrayList<>();

        for (Card c : doubles) {
            PlayerCardCollectionResource resource = new PlayerCardCollectionResource();

            resource.setCardId(c.getId());
            resource.setCardLabel(c.getLabel());
            resource.setCardQuantity(this.playerCardRepository.getPlayerCard(player.getId(), c.getId()).getQuantity());
            resource.setCollectionLabel(this.collectionRepository.findById(c.getCollectionId()).orElseThrow().getName());

            resources.add(resource);
        }

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/player/cards/trade/create")
    public ResponseEntity<Trade> createTrade(@RequestParam String token, @RequestParam int cardProposedId) {
        Player player = this.playerRepository.findByToken(token).orElseThrow();

        // Check if player has the card
        if(!this.playerCardRepository.existsByCardIdAndPlayerId(cardProposedId, player.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Trade trade = new Trade();

        trade.setTransmitterId(player.getId());
        trade.setTransmitterCardId(cardProposedId);
        trade.setTradeStatus("TRADE_CREATED");
        trade.setRecipientId(-1L);
        trade.setRecipientCardId(-1);

        trade = this.tradeRepository.save(trade);

        return new ResponseEntity<>(trade, HttpStatus.OK);
    }
}
