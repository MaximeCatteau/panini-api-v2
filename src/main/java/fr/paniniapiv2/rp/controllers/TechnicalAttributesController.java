package fr.paniniapiv2.rp.controllers;

import fr.paniniapiv2.rp.resources.UpdateTechnicalFieldPlayerAttributesResource;
import fr.paniniapiv2.rp.resources.UpdateTechnicalGoalKeeperAttributesResource;
import fr.paniniapiv2.rp.services.TechnicalAttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class TechnicalAttributesController {
    @Autowired
    TechnicalAttributesService technicalAttributesService;

    @CrossOrigin
    @PostMapping("/rpbinouze/attributes/technical/fieldplayer/update")
    public ResponseEntity<Void> updateFieldPlayerTechnicalAttributes(@RequestBody UpdateTechnicalFieldPlayerAttributesResource resource) {
        this.technicalAttributesService.updateFieldPlayerTechnicalAttributes(resource);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/rpbinouze/attributes/technical/goalkeeper/update")
    public ResponseEntity<Void> updateGoalKeeperTechnicalAttributes(@RequestBody UpdateTechnicalGoalKeeperAttributesResource resource) {
        this.technicalAttributesService.updateGoalkeeperTechnicalAttributes(resource);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
