package fr.paniniapiv2.controllers;

import fr.paniniapiv2.PlayerResource;
import fr.paniniapiv2.db.*;
import fr.paniniapiv2.db.Collection;
import fr.paniniapiv2.repositories.*;
import fr.paniniapiv2.resources.CollectionLadderResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class CollectionController {
    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerCollectionRepository playerCollectionRepository;

    @Autowired
    PlayerCareerRepository playerCareerRepository;

    @Autowired
    CardRepository cardRepository;

    @Autowired
    PlayerTitleRepository playerTitleRepository;

    @Autowired
    TitleRepository titleRepository;

    @CrossOrigin
    @GetMapping("/collections/category")
    public List<Collection> getCollectionByCategoryId(@RequestParam int categoryId) {
        return this.collectionRepository.findByCategoryId(categoryId);
    }

    @CrossOrigin
    @GetMapping("/collections/all")
    public List<Collection> getAllCollections() {
        return this.collectionRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/collections")
    public Collection getCollectionById(@RequestParam int collectionId) {
        return this.collectionRepository.findById(collectionId).orElseThrow();
    }

    @CrossOrigin
    @GetMapping("/collections/paid")
    public List<Collection> getCollectionToPay(){
        return this.collectionRepository.getCollectionsToPay();
    }

    @CrossOrigin
    @GetMapping("/collection/ladder")
    public ResponseEntity<List<CollectionLadderResource>> getLadderByCollection(@RequestParam int collectionId) {

        List<Object[]> objectTest = this.collectionRepository.getCardCountsByCollection(collectionId);
        List<CollectionLadderResource> resource = new ArrayList<>();

        System.out.println(objectTest.size());

        for(Object[] o : objectTest) {
            Integer count = ((BigInteger) o[0]).intValue();
            Long playerId = ((BigInteger) o[1]).longValue();

            Player p = this.playerRepository.getById(playerId);

            CollectionLadderResource clr = new CollectionLadderResource();

            clr.setPlayerId(playerId);
            clr.setCardCount(count);
            clr.setPlayerName(p.getUsername());

            PlayerTitle playerTitle = this.playerTitleRepository.getSelectedTitleByPlayerId(playerId);

            Title titleSelected = new Title();

            if (playerTitle != null) {
                titleSelected = this.titleRepository.getById(playerTitle.getTitleId());
                clr.setTitleSelected(titleSelected.getLabel());
                clr.setTitleSelectedColor(titleSelected.getColor());
            }

            if (!p.getUsername().equals("Admin")) {
                resource.add(clr);
            }
        }

        Collections.sort(resource, Comparator.comparing(CollectionLadderResource::getCardCount).reversed());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/collection/ladder/general")
    public ResponseEntity<List<CollectionLadderResource>> getGeneralLadder() {

        List<Object[]> objectTest = this.collectionRepository.getCardCountsForLadder();
        List<CollectionLadderResource> resource = new ArrayList<>();

        System.out.println(objectTest.size());

        for(Object[] o : objectTest) {
            Integer count = ((BigInteger) o[0]).intValue();
            Long playerId = ((BigInteger) o[1]).longValue();

            Player p = this.playerRepository.getById(playerId);

            CollectionLadderResource clr = new CollectionLadderResource();

            clr.setPlayerId(playerId);
            clr.setCardCount(count);
            clr.setPlayerName(p.getUsername());

            PlayerTitle playerTitle = this.playerTitleRepository.getSelectedTitleByPlayerId(playerId);

            Title titleSelected = new Title();

            if (playerTitle != null) {
                titleSelected = this.titleRepository.getById(playerTitle.getTitleId());
                clr.setTitleSelected(titleSelected.getLabel());
                clr.setTitleSelectedColor(titleSelected.getColor());
            }

            if (!p.getUsername().equals("Admin")) {
                resource.add(clr);
            }
        }

        Collections.sort(resource, Comparator.comparing(CollectionLadderResource::getCardCount).reversed());

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/collections/owned/category")
    public List<Collection> getCollectionsOwnedByPlayerByCategory(@RequestBody PlayerResource player, @RequestParam int categoryId) {
        Player p = this.playerRepository.findByUsername(player.getUsername()).orElseThrow();
        return this.collectionRepository.getCollectionsOwnedByPlayerByCategory(p.getId(), categoryId);
    }

    @CrossOrigin
    @PostMapping("/collections/owned")
    public List<Collection> getCollectionsOwnedByPlayer(@RequestParam String token) {
        Player p = this.playerRepository.findByToken(token).orElseThrow();
        return this.collectionRepository.getCollectionsOwnedByPlayer(p.getId());
    }

    @CrossOrigin
    @PostMapping("/collections/alreadyPaid")
    public List<Collection> getCollectionsAlreadyPaidByPlayer(@RequestBody PlayerResource player) {
        Player p = this.playerRepository.findByUsername(player.getUsername()).orElseThrow();
        return this.collectionRepository.getCollectionsAlreadyPaidByPlayer(p.getId());
    }

    @CrossOrigin
    @PostMapping("/collections/notAlreadyPaid")
    public List<Collection> getCollectionsNotAlreadyPaidByPlayer(@RequestParam String token) {
        Player p = this.playerRepository.findByToken(token).orElseThrow();
        return this.collectionRepository.getCollectionsNotAlreadyPaidByPlayer(p.getId());
    }

    @CrossOrigin
    @PostMapping("/collections/buy")
    public ResponseEntity<Collection> buyCollection(@RequestParam String token, @RequestParam Integer collectionId) {
        Player p = this.playerRepository.findByToken(token).orElseThrow();
        Collection c = this.collectionRepository.findById(collectionId).orElseThrow();
        PlayerCareer playerCareer = this.playerCareerRepository.findByPlayerId(p.getId());

        // Check si le joueur a assez de thune
        if (p.getCashCard() < c.getPrice()) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }

        // Ajouter la collection à playerCollection
        PlayerCollection pc = new PlayerCollection();
        pc.setCollectionId(c.getId());
        pc.setPlayerId(p.getId());

        this.playerCollectionRepository.save(pc);

        // Retirer le prix de la collection au cash du joueur
        p.setCashCard(p.getCashCard() - c.getPrice());
        this.playerRepository.save(p);

        // Mise a jour des states du joueur
        // -- cash spent
        playerCareer.setCashSpent(playerCareer.getCashSpent() + c.getPrice());

        // -- collection owned
        playerCareer.setCollections(playerCareer.getCollections() + 1);

        // -- missing cards
        playerCareer.setMissingCards(playerCareer.getMissingCards() + this.cardRepository.countByCollectionId(c.getId()));

        this.playerCareerRepository.save(playerCareer);

        return new ResponseEntity<>(c, HttpStatus.OK);
    }
}
