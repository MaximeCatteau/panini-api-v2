package fr.paniniapiv2.rp.controllers;

import fr.paniniapiv2.rp.resources.UpdateMentalAttributesResource;
import fr.paniniapiv2.rp.resources.UpdatePhysicalAttributesResource;
import fr.paniniapiv2.rp.services.MentalAttributesService;
import fr.paniniapiv2.rp.services.PhysicalAttributesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class MentalAttributesController {
    @Autowired
    MentalAttributesService mentalAttributesService;

    @CrossOrigin
    @PostMapping("/rpbinouze/attributes/mental/update")
    public ResponseEntity<Void> updatePhysicalAttributes(@RequestBody UpdateMentalAttributesResource resource) {
        this.mentalAttributesService.updateMentalAttributes(resource);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
