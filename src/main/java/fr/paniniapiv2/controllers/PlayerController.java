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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.MessageDigest;
import java.util.List;

@RestController
public class PlayerController {
    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    PlayerCollectionRepository playerCollectionRepository;

    private static final String ALGORITHM = "SHA";

    @PostMapping("/signup")
    public ResponseEntity<Player> registerPlayer(@RequestBody PlayerResource resource) {
        if (playerRepository.existsByUsername(resource.getUsername())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        resource.setPassword(getEncryptedPassword(resource.getPassword()));

        Player player = Player.fromResource(resource);

        player = playerRepository.save(player);

        // GET FREE COLLECTIONS
        List<Collection> freeCollections = this.collectionRepository.getFreeCollections();

        // ADD FREE COLLECTIONS FOR USER
        for (Collection c : freeCollections) {
            PlayerCollection pc = new PlayerCollection();

            pc.setPlayerId(player.getId());
            pc.setCollectionId(c.getId());

            this.playerCollectionRepository.save(pc);
        }

        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @PostMapping("/signin")
    public ResponseEntity<Player> connectPlayer(@RequestBody PlayerResource resource) {
        if (!playerRepository.existsByUsername(resource.getUsername())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Player playerAssociated = playerRepository.findByUsername(resource.getUsername()).get();

        if (playerAssociated.getPassword().equals(getEncryptedPassword(resource.getPassword()))) {
            return new ResponseEntity<>(playerAssociated, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    private String getEncryptedPassword(String rawPassword) {
        byte[] plainText = rawPassword.getBytes();

        try {
            MessageDigest md = MessageDigest.getInstance(ALGORITHM);

            md.reset();
            md.update(plainText);
            byte[] encodedPassword = md.digest();

            StringBuilder sb = new StringBuilder();

            for (int i = 0; i < encodedPassword.length; i++) {
                if ((encodedPassword[i] & 0xff) < 0x10) {
                    sb.append("0");
                }

                sb.append(Long.toString(encodedPassword[i] & 0xff, 16));
            }

            return sb.toString();
        } catch (Exception e) {
            e.getMessage();
        }

        return "";
    }
}
