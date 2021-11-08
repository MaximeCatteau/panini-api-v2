package fr.paniniapiv2.controllers;

import fr.paniniapiv2.PlayerResource;
import fr.paniniapiv2.db.Card;
import fr.paniniapiv2.db.Player;
import fr.paniniapiv2.db.Trade;
import fr.paniniapiv2.repositories.CardRepository;
import fr.paniniapiv2.repositories.PlayerCardRepository;
import fr.paniniapiv2.repositories.PlayerRepository;
import fr.paniniapiv2.repositories.TradeRepository;
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
    @Autowired
    TradeRepository tradeRepository;

    @CrossOrigin
    @GetMapping("/player/cards")
    public ResponseEntity<List<Card>> getAllPlayerCards(@RequestParam String playerName) {
        Player player = playerRepository.findByUsername(playerName).get();

        List<Card> playerCards = this.cardRepository.getAllPlayerCards(player.getId());

        return new ResponseEntity<>(playerCards, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/player/cards/collection")
    public ResponseEntity<List<Card>> getPlayerCardsByCollectionId(@RequestBody PlayerResource playerResource, @RequestParam int collectionId) {
        Player player = playerRepository.findByUsername(playerResource.getUsername()).get();

        List<Card> playerCards = this.cardRepository.getPlayerCardsByCollectionId(player.getId(), collectionId);

        return new ResponseEntity<>(playerCards, HttpStatus.OK);
    }

    @PostMapping("/player/cards/doubles")
    public ResponseEntity<List<Card>> getPlayerDoubleCards(@RequestBody PlayerResource playerResource) {
        Player player = playerRepository.findByUsername(playerResource.getUsername()).get();

        List<Card> doubles = this.cardRepository.getDoubleCardsForPlayer(player.getId());

        return new ResponseEntity<>(doubles, HttpStatus.OK);
    }

    @PostMapping("/player/cards/trade/create")
    public ResponseEntity<Trade> createTrade(@RequestBody PlayerResource playerResource, @RequestParam int cardProposedId) {
        Player player = this.playerRepository.findByUsername(playerResource.getUsername()).orElseThrow();

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

    @PostMapping("/player/cards/trade/propose")
    public ResponseEntity<Trade> proposeTrade(@RequestBody PlayerResource playerResource, int cardProposedId) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/player/cards/trade")
    public ResponseEntity<Card> makeTrade(@RequestBody PlayerResource playerResource) {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
