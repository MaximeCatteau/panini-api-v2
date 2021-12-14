package fr.paniniapiv2.controllers;

import fr.paniniapiv2.db.*;
import fr.paniniapiv2.enums.CodeStatus;
import fr.paniniapiv2.enums.CodeType;
import fr.paniniapiv2.repositories.*;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CompletableFuture;

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

    @Autowired
    PlayerCareerRepository playerCareerRepository;

    @Autowired
    LadderRepository ladderRepository;

    @Autowired
    CollectionRepository collectionRepository;

    /**
     * DISCORD
     * @param resource
     * @return
     */
    DiscordApi api = new DiscordApiBuilder()
            .setToken("OTEwMDg2MTQyNTgwMzY3Mzgw.YZNtxA.qZBFOb7Ro2yct4Ddv_AQAEYTs50")
            .login().join();

    @CrossOrigin
    @PostMapping("/generate")
    public ResponseEntity<Code> generateCode(@RequestParam String token, @RequestParam Long userId) {
        Player player = this.playerRepository.findByToken(token).orElseThrow();
        Player codeReceiver = this.playerRepository.findById(userId).orElseThrow();

        if (!player.getRole().equals("ADMIN")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Code code = new Code();

        code.setPlayerAssociated(codeReceiver.getId());
        code.setCodeType(CodeType.CARD);
        code.setStatus(CodeStatus.GENERATED);
        code.setValue(code.generateRandomCode());

        CompletableFuture<User> user = api.getUserById(codeReceiver.getDiscordId());
        CompletableFuture<User> admin = api.getUserById(player.getDiscordId());

        try {
            PrivateChannel pcAdmin = admin.get().openPrivateChannel().get();
            pcAdmin.sendMessage(codeReceiver.getUsername() + " a reçu un code !");
            PrivateChannel pcUser = user.get().openPrivateChannel().get();
            pcUser.sendMessage("Félicitations ! Vous avez reçu un code : " + code.getValue());
        } catch(Exception e) {
            e.printStackTrace();
        }

        codeRepository.save(code);

        return new ResponseEntity<>(code, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/consume")
    public ResponseEntity<List<Card>> consumeCode(@RequestParam String token, @RequestParam String code) {
        List<Card> cards = new ArrayList<>();
        Player player = this.playerRepository.findByToken(token).orElseThrow();

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

                CompletableFuture<User> user = api.getUserById("185790407156826113");

                try {
                    PrivateChannel pc = user.get().openPrivateChannel().get();
                    Collection cardCollection = this.collectionRepository.getById(c.getCollectionId());
                    pc.sendMessage(player.getUsername() + " a reçu la carte " + c.getLabel() + " (" + cardCollection.getName() + ")");

                } catch(Exception e) {
                    e.printStackTrace();
                }

                codeRepository.delete(foundCode);
                break;
            default:
                break;
        }

        // RETOURNER LES CARTES GAGNEES
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/buy/pack")
    public ResponseEntity<List<Card>> buyCardPack(@RequestParam String token, @RequestParam int packNumber){
        Player player = this.playerRepository.findByToken(token).orElseThrow();
        PlayerCareer playerCareer = this.playerCareerRepository.findByPlayerId(player.getId());
        List<Card> cardsObtained = new ArrayList<>();

        // SELON LE TYPE DE PACK
        switch (packNumber) {
            // PACK 3 CARTES
            case 0:
                if (player.getCashCard() < 75) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }

                for (int i = 0; i<3; i++) {
                    String rarity = getRandomRarity();
                    Card c = cardRepository.findRandomCard(player.getId(), rarity);
                    cardsObtained.add(c);
                    insertCard(player.getId(), c.getId());
                }

                player.setCashCard(player.getCashCard() - 75);
                playerCareer.setCashSpent(playerCareer.getCashSpent() + 75);

                break;
            // PACK 5 CARTES DONT RARE EPIC
            case 1:
                if (player.getCashCard() < 150) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
                for (int i = 0; i<5; i++) {
                    String rarity = getRandomRarity();

                    if (i == 4) {
                        rarity = getRareOrEpic();
                    }

                    Card c = cardRepository.findRandomCard(player.getId(), rarity);
                    cardsObtained.add(c);
                    insertCard(player.getId(), c.getId());
                }

                player.setCashCard(player.getCashCard() - 150);
                playerCareer.setCashSpent(playerCareer.getCashSpent() + 150);

                break;
            case 2:
                if (player.getCashCard() < 200) {
                    return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
                }
                for (int i = 0; i<5; i++) {
                    String rarity = getRandomRarity();

                    if (i == 4) {
                        rarity = getRareOrEpic();
                    }

                    Card c = cardRepository.findRandomCardWithCategory(player.getId(), rarity, 1);
                    cardsObtained.add(c);
                    insertCard(player.getId(), c.getId());
                }

                player.setCashCard(player.getCashCard() - 200);
                playerCareer.setCashSpent(playerCareer.getCashSpent() + 200);

                break;
        }

        this.playerRepository.save(player);

        CompletableFuture<User> user = api.getUserById("185790407156826113");

        try {
            PrivateChannel pc = user.get().openPrivateChannel().get();
            String message = player.getUsername() + " a acheté un pack " + packNumber + " et a obtenu : ";

            for (Card card : cardsObtained) {
                Collection cardCollection = this.collectionRepository.getById(card.getCollectionId());
                message += "\n- " + card.getLabel() + "(" + cardCollection.getName() + ")";
            }

            pc.sendMessage(message);

        } catch(Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(cardsObtained, HttpStatus.OK);
    }

    private void insertCard(Long playerId, int cardId) {
        // on check si la carte existe pour le joueur
        // si oui : augmenter la quantité
        if (this.playerCardRepository.existsByCardIdAndPlayerId(cardId, playerId)) {
            PlayerCard playerCard = this.playerCardRepository.findByCardIdAndPlayerId(cardId, playerId).get();

            playerCard.setQuantity(playerCard.getQuantity() + 1);

            this.playerCardRepository.save(playerCard);
        } else {
            Player player = this.playerRepository.findById(playerId).orElseThrow();
            PlayerCard playerCard = new PlayerCard();
            Card card = this.cardRepository.findById(cardId);
            Ladder ladder = this.ladderRepository.findByPlayerUsername(player.getUsername());

            PlayerCareer playerCareer = this.playerCareerRepository.findByPlayerId(playerId);

            playerCard.setCardId(cardId);
            playerCard.setPlayerId(playerId);
            playerCard.setQuantity(1);

            // -- player career : cards owned
            playerCareer.setTotalCard(playerCareer.getTotalCard() + 1);
            // -- player career ! missing cards
            playerCareer.setMissingCards(playerCareer.getMissingCards() - 1);
            // -- collection completed ?
            int collectionSize = this.cardRepository.findByCollectionId(card.getCollectionId()).size();
            int countOfCardsOwnedByPlayerOnCollection = this.playerCardRepository.getNumberOfPlayerCardsOnCollection(playerId, card.getCollectionId());

            if (collectionSize == countOfCardsOwnedByPlayerOnCollection) {
                playerCareer.setCollectionsCompleted(playerCareer.getCollectionsCompleted() + 1);
            }

            // -- ladder : increase card count
            ladder.setCardCount(ladder.getCardCount() + 1);

            this.ladderRepository.save(ladder);

            this.playerCareerRepository.save(playerCareer);

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

    private String getRareOrEpic() {
        Random random = new Random();
        int i = random.nextInt(100) + 1;

        if (i >= 1 && i < 20) {
            return "EPIC";
        } else {
            return "RARE";
        }
    }
}
