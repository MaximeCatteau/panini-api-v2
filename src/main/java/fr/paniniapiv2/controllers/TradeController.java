package fr.paniniapiv2.controllers;

import fr.paniniapiv2.PlayerResource;
import fr.paniniapiv2.db.*;
import fr.paniniapiv2.repositories.*;
import fr.paniniapiv2.resources.TradePropositionResource;
import fr.paniniapiv2.resources.TradeStepOneResource;
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
import java.util.concurrent.CompletableFuture;

@RestController
public class TradeController {
    @Autowired
    TradeRepository tradeRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerCardRepository playerCardRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    TradePropositionRepository tradePropositionRepository;

    @Autowired
    LadderRepository ladderRepository;

    @Autowired
    PlayerCareerRepository playerCareerRepository;

    /**
     * DISCORD
     * @param resource
     * @return
     */
    DiscordApi api = new DiscordApiBuilder()
            .setToken("OTEwMDg2MTQyNTgwMzY3Mzgw.YZNtxA.qZBFOb7Ro2yct4Ddv_AQAEYTs50")
            .login().join();

    @CrossOrigin
    @GetMapping("/trades/stepOne")
    public ResponseEntity<List<TradeStepOneResource>> getAllStepOneTrades() {
        List<Trade> trades = this.tradeRepository.getAllTradesStepOne();
        List<TradeStepOneResource> resources = new ArrayList<>();

        for (Trade t : trades) {
            Card proposedCard = this.cardRepository.findById(t.getTransmitterCardId());
            TradeStepOneResource resource = new TradeStepOneResource();
            resource.setTradeId(t.getId());
            resource.setTransmitterId(t.getTransmitterId());
            resource.setCardId(t.getTransmitterCardId());
            resource.setTransmitterName(this.playerRepository.findById(t.getTransmitterId()).orElseThrow().getUsername());
            resource.setCardLabel(proposedCard.getLabel());
            resource.setCollectionLabel(this.collectionRepository.findById(proposedCard.getCollectionId()).orElseThrow().getName());
            resource.setPropositionCount(this.tradeRepository.getPropositionCountForTrade(t.getId()));

            resources.add(resource);
        }

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/trades/player")
    public ResponseEntity<List<TradeStepOneResource>> getAllStepOneTradesForPlayer(@RequestParam String token) {
        Player player = this.playerRepository.findByToken(token).orElseThrow();
        List<Trade> trades = this.tradeRepository.getAllTradesStepOneForPlayer(player.getId());
        List<TradeStepOneResource> resources = new ArrayList<>();

        for (Trade t : trades) {
            Card proposedCard = this.cardRepository.findById(t.getTransmitterCardId());
            TradeStepOneResource resource = new TradeStepOneResource();
            resource.setTransmitterId(t.getTransmitterId());
            resource.setCardId(t.getTransmitterCardId());
            resource.setTransmitterName(this.playerRepository.findById(t.getTransmitterId()).orElseThrow().getUsername());
            resource.setCardLabel(proposedCard.getLabel());
            resource.setCollectionLabel(this.collectionRepository.findById(proposedCard.getCollectionId()).orElseThrow().getName());
            resource.setPropositionCount(this.tradeRepository.getPropositionCountForTrade(t.getId()));

            resources.add(resource);
        }

        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/trades/proposition")
    public ResponseEntity<TradeProposition> createTradeProposition(@RequestParam String token, @RequestParam int tradeId, @RequestParam int traderCardId) {
        Player player = this.playerRepository.findByToken(token).orElseThrow();
        Trade trade = this.tradeRepository.findById(tradeId).orElseThrow();
        Player tradeInitiater = this.playerRepository.findById(trade.getTransmitterId()).orElseThrow();

        TradeProposition tradeProposition = new TradeProposition();
        tradeProposition.setTradeId(tradeId);
        tradeProposition.setCardId(traderCardId);
        tradeProposition.setRecipientId(player.getId());

        this.tradePropositionRepository.save(tradeProposition);

        trade.setTradeStatus("TRADE_PROPOSED");

        this.tradeRepository.save(trade);

        CompletableFuture<User> user = api.getUserById(tradeInitiater.getDiscordId());

        try {
            PrivateChannel pc = user.get().openPrivateChannel().get();
            pc.sendMessage(player.getUsername() + " vous a fait une proposition pour un échange !");

        } catch(Exception e) {
            e.printStackTrace();
        }

        return new ResponseEntity<>(tradeProposition, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/trades/propositions")
    public ResponseEntity<List<TradePropositionResource>> getTradePropositionsForPlayer(@RequestParam String token) {
        Player p = this.playerRepository.findByToken(token).orElseThrow();

        // Player trades with status PROPOSED
        List<Trade> playerTradeProposed = this.tradeRepository.getAllTradesProposedForPlayer(p.getId());

        // Player propositions
        List<TradeProposition> playerTradeProposition = this.tradePropositionRepository.getAllTradePropositionsByPlayer(p.getId());

        // Final resource
        List<TradePropositionResource> resource = new ArrayList<>();

        // Adding trade proposed to resource
        for (Trade t : playerTradeProposed) {
            List<TradeProposition> tradePropositionsForTrade = this.tradePropositionRepository.getPropositionsForTrade(t.getId());
            for (TradeProposition tp : tradePropositionsForTrade) {
                TradePropositionResource r = new TradePropositionResource();
                Card transmitterCard = this.cardRepository.findById(t.getTransmitterCardId());
                Card proposedCard = this.cardRepository.findById(tp.getCardId());

                r.setTradeId(t.getId());
                r.setTradePropositionId(tp.getId());

                r.setCardConcernedId(t.getTransmitterCardId());
                r.setCardConcernedLabel(transmitterCard.getLabel());
                r.setCardConcernedCollectionLabel(this.collectionRepository.findById(transmitterCard.getCollectionId()).orElseThrow().getName());

                r.setCardProposedId(tp.getCardId());
                r.setCardProposedLabel(proposedCard.getLabel());
                r.setCardProposedCollectionLabel(this.collectionRepository.findById(proposedCard.getCollectionId()).orElseThrow().getName());

                r.setPropositionEmitter(this.playerRepository.findById(tp.getRecipientId()).orElseThrow().getUsername());

                resource.add(r);
            }
        }

        // Adding trade propositions to resource
        for (TradeProposition tp : playerTradeProposition) {
            TradePropositionResource r = new TradePropositionResource();
            Trade tradeAssociated = this.tradeRepository.findById(tp.getTradeId()).orElseThrow();
            Card transmitterCard = this.cardRepository.findById(tradeAssociated.getTransmitterCardId());
            Card recipientCard = this.cardRepository.findById(tp.getCardId());

            r.setTradeId(tp.getTradeId());
            r.setTradePropositionId(tp.getId());

            r.setCardConcernedId(transmitterCard.getId());
            r.setCardConcernedLabel(transmitterCard.getLabel());
            r.setCardConcernedCollectionLabel(this.collectionRepository.findById(transmitterCard.getCollectionId()).orElseThrow().getName());

            r.setCardProposedId(recipientCard.getId());
            r.setCardProposedLabel(recipientCard.getLabel());
            r.setCardProposedCollectionLabel(this.collectionRepository.findById(recipientCard.getCollectionId()).orElseThrow().getName());

            r.setPropositionEmitter("Vous");

            resource.add(r);
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/trades/trade/make")
    public ResponseEntity<Card> makeTrade(@RequestParam String token, @RequestParam int tradeId, @RequestParam int tradePropositionId) {
        // Vérification du trade
        Trade trade = this.tradeRepository.findById(tradeId).orElseThrow();
        TradeProposition tradeProposition = this.tradePropositionRepository.findById(tradePropositionId).orElseThrow();
        Player transmitter = this.playerRepository.findByToken(token).orElseThrow();
        Player recipient = this.playerRepository.findById(tradeProposition.getRecipientId()).orElseThrow();
        Card transmitterCard = this.cardRepository.findById(trade.getTransmitterCardId());
        Card recipientCard = this.cardRepository.findById(tradeProposition.getCardId());
        PlayerCareer recipientCareer = this.playerCareerRepository.findByPlayerId(recipient.getId());
        PlayerCareer transmitterCareer = this.playerCareerRepository.findByPlayerId(transmitter.getId());

        // Ajouter la carte au recipient
        this.insertCard(recipient.getId(), transmitterCard.getId());
        CompletableFuture<User> user = api.getUserById(recipient.getDiscordId());

        try {
            PrivateChannel pc = user.get().openPrivateChannel().get();
            pc.sendMessage("Votre proposition d'échange a été acceptée par " + transmitter.getUsername() + ". Vous recevez la carte " + transmitterCard.getLabel() + " !");

        } catch(Exception e) {
            e.printStackTrace();
        }

        // Retirer la carte au recipient
        this.removeCard(recipient.getId(), recipientCard.getId());

        // Ajouter la carte au transmitter
        this.insertCard(transmitter.getId(), recipientCard.getId());

        // Retirer la carte au transmitter
        this.removeCard(transmitter.getId(), transmitterCard.getId());

        // Recipient player career : trade
        recipientCareer.setTradeCompleted(recipientCareer.getTradeCompleted() + 1);
        this.playerCareerRepository.save(recipientCareer);

        // Transmitter player career : trade + card count + missing cards
        transmitterCareer.setTradeCompleted(transmitterCareer.getTradeCompleted() + 1);
        this.playerCareerRepository.save(transmitterCareer);

        // Retirer trade proposition
        this.tradePropositionRepository.delete(tradeProposition);

        // Trade finished + valeurs finales
        trade.setRecipientId(recipient.getId());
        trade.setRecipientCardId(recipientCard.getId());
        trade.setTradeStatus("TRADE_FINISHED");
        this.tradeRepository.save(trade);

        return new ResponseEntity<>(recipientCard, HttpStatus.OK);
    }

    @CrossOrigin
    @DeleteMapping("/trades/proposition/refuse")
    public ResponseEntity<TradeProposition> refuseProposition(@RequestParam int tradePropositionId) {
        TradeProposition tradeProposition = this.tradePropositionRepository.findById(tradePropositionId).orElseThrow();

        this.tradePropositionRepository.delete(tradeProposition);

        return new ResponseEntity<>(tradeProposition, HttpStatus.OK);
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

    private void removeCard(Long playerId, int cardId) {
        PlayerCard playerCard = this.playerCardRepository.findByCardIdAndPlayerId(cardId, playerId).orElseThrow();

        if (playerCard.getQuantity() > 1) {
            playerCard.setQuantity(playerCard.getQuantity() - 1);
            this.playerCardRepository.save(playerCard);
        } else if (playerCard.getQuantity() == 1) {
            this.playerCardRepository.delete(playerCard);
        }
    }
}
