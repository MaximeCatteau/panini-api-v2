package fr.paniniapiv2.controllers;

import fr.paniniapiv2.PlayerResource;
import fr.paniniapiv2.db.Collection;
import fr.paniniapiv2.db.Player;
import fr.paniniapiv2.db.PlayerCollection;
import fr.paniniapiv2.repositories.CollectionRepository;
import fr.paniniapiv2.repositories.PlayerCollectionRepository;
import fr.paniniapiv2.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CollectionController {
    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    PlayerCollectionRepository playerCollectionRepository;

    @GetMapping("/collections/category")
    public List<Collection> getCollectionByCategoryId(@RequestParam int categoryId) {
        return this.collectionRepository.findByCategoryId(categoryId);
    }

    @GetMapping("/collections")
    public Collection getCollectionById(@RequestParam int collectionId) {
        return this.collectionRepository.findById(collectionId).orElseThrow();
    }

    @GetMapping("/collections/paid")
    public List<Collection> getCollectionToPay(){
        return this.collectionRepository.getCollectionsToPay();
    }

    @PostMapping("/collections/owned")
    public List<Collection> getCollectionsOwnedByPlayer(@RequestBody PlayerResource player, @RequestParam int categoryId) {
        Player p = this.playerRepository.findByUsername(player.getUsername()).orElseThrow();
        return this.collectionRepository.getCollectionsOwnedByPlayer(p.getId(), categoryId);
    }

    @PostMapping("/collections/alreadyPaid")
    public List<Collection> getCollectionsAlreadyPaidByPlayer(@RequestBody PlayerResource player) {
        Player p = this.playerRepository.findByUsername(player.getUsername()).orElseThrow();
        return this.collectionRepository.getCollectionsAlreadyPaidByPlayer(p.getId());
    }

    @PostMapping("/collections/notAlreadyPaid")
    public List<Collection> getCollectionsNotAlreadyPaidByPlayer(@RequestBody PlayerResource player) {
        Player p = this.playerRepository.findByUsername(player.getUsername()).orElseThrow();
        return this.collectionRepository.getCollectionsNotAlreadyPaidByPlayer(p.getId());
    }

    @PostMapping("/collections/buy")
    public ResponseEntity<Collection> buyCollection(@RequestBody PlayerResource player, @RequestParam Integer collectionId) {
        Player p = this.playerRepository.findByUsername(player.getUsername()).orElseThrow();
        Collection c = this.collectionRepository.findById(collectionId).orElseThrow();

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
        p.setCashCard(p.getCashCard() - (int) c.getPrice());
        this.playerRepository.save(p);

        return new ResponseEntity<>(c, HttpStatus.OK);
    }
}
