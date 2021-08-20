package fr.paniniapiv2.controllers;

import fr.paniniapiv2.PlayerResource;
import fr.paniniapiv2.db.Card;
import fr.paniniapiv2.db.Code;
import fr.paniniapiv2.db.Player;
import fr.paniniapiv2.db.PlayerCard;
import fr.paniniapiv2.enums.CodeStatus;
import fr.paniniapiv2.enums.CodeType;
import fr.paniniapiv2.repositories.CardRepository;
import fr.paniniapiv2.repositories.CodeRepository;
import fr.paniniapiv2.repositories.PlayerCardRepository;
import fr.paniniapiv2.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
public class CodeController {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CodeRepository codeRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    PlayerCardRepository playerCardRepository;

    @PostMapping("/generate")
    public Code generateCode(@RequestBody PlayerResource resource) {

        Player playerAssociated = playerRepository.findByUsername(resource.getUsername()).get();

        Code code = new Code();

        code.setPlayerAssociated(playerAssociated.getId());
        code.setCodeType(CodeType.CARD);
        code.setStatus(CodeStatus.GENERATED);
        code.setValue(code.generateRandomCode());

        codeRepository.save(code);

        return code;
    }

    @PostMapping("/consume")
    public ResponseEntity<List<Card>> consumeCode(@RequestBody PlayerResource playerResource, @RequestParam String code) {
        List<Card> cards = new ArrayList<>();
        Player player = this.playerRepository.findByUsername(playerResource.getUsername()).orElseThrow();

        // Checker si le joueur existe
        if (!playerRepository.existsByUsername(player.getUsername())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // Checker si le code existe
        if (!codeRepository.existsByValue(code)) {
            return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }

        // Checker si le code est bien associé au joueur
        if (!codeRepository.existsByValueAndPlayerAssociated(code, player.getId())) {
            return new ResponseEntity<>(HttpStatus.UNAVAILABLE_FOR_LEGAL_REASONS);
        }

        Code foundCode = this.codeRepository.findByValue(code);

        // Checker le type de code
        switch (foundCode.getCodeType()) {
            case CARD:
                String rarity = getRandomRarity();

                Card c = cardRepository.findRandomCard(player.getId(), rarity);
                cards.add(c);

                insertCard(player.getId(), c.getId());

                codeRepository.delete(foundCode);
                break;
            case CARD_PACK:
                String rarity1 = getRandomRarity();
                Card c1 = cardRepository.findRandomCard(player.getId(), rarity1);

                String rarity2 = getRandomRarity();
                Card c2 = cardRepository.findRandomCard(player.getId(), rarity2);

                String rarity3 = getRandomRarity();
                Card c3 = cardRepository.findRandomCard(player.getId(), rarity3);

                cards.add(c1);
                cards.add(c2);
                cards.add(c3);

                insertCard(player.getId(), c1.getId());
                insertCard(player.getId(), c2.getId());
                insertCard(player.getId(), c3.getId());

                codeRepository.delete(foundCode);
                break;
            default:
                break;
        }

        // RETOURNER LES CARTES GAGNEES
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    private void insertCard(Long playerId, int cardId) {
        // on check si la carte existe pour le joueur
        // si oui : augmenter la quantité
        if (this.playerCardRepository.existsByCardIdAndPlayerId(cardId, playerId)) {
            PlayerCard playerCard = this.playerCardRepository.findByCardIdAndPlayerId(cardId, playerId).get();

            playerCard.setQuantity(playerCard.getQuantity() + 1);

            this.playerCardRepository.save(playerCard);
        } else {
            PlayerCard playerCard = new PlayerCard();

            playerCard.setCardId(cardId);
            playerCard.setPlayerId(playerId);
            playerCard.setQuantity(1);

            this.playerCardRepository.save(playerCard);
        }

        // si non : ajouter à la base
    }

    private String getRandomRarity() {
        Random random = new Random();
        int i = random.nextInt(100) + 1;

        if (i == 1) {
            return "EPIC";
        } else if (i > 1 && i <=6) {
            return "RARE";
        } else {
            return "NORMAL";
        }
    }
}
