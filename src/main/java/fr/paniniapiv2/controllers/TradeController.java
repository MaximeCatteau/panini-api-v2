package fr.paniniapiv2.controllers;

import fr.paniniapiv2.PlayerResource;
import fr.paniniapiv2.db.Player;
import fr.paniniapiv2.db.PlayerCard;
import fr.paniniapiv2.db.Trade;
import fr.paniniapiv2.repositories.PlayerCardRepository;
import fr.paniniapiv2.repositories.PlayerRepository;
import fr.paniniapiv2.repositories.TradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TradeController {
    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerCardRepository playerCardRepository;

    @GetMapping("/trades")
    public List<Trade> getAllTrades() {
        return this.tradeRepository.getAllTrades();
    }

    @PostMapping("/trade")
    public ResponseEntity<Trade> createTrade(@RequestBody PlayerResource resource, @RequestParam("cardId") int cardId) {
        Player player = this.playerRepository.findByUsername(resource.getUsername()).orElseThrow();
        PlayerCard playerCard = this.playerCardRepository.getPlayerCard(player.getId(), cardId);

        // Checker si le joueur possède bien la carte EN DOUBLE
        if (playerCard == null || playerCard.getQuantity() <= 1){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }
}
