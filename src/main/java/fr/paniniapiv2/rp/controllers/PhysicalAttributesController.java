package fr.paniniapiv2.rp.controllers;

import fr.paniniapiv2.rp.resources.UpdatePhysicalAttributesResource;
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
public class PhysicalAttributesController {
    @Autowired
    PhysicalAttributesService physicalAttributesService;

    @CrossOrigin
    @PostMapping("/rpbinouze/attributes/physical/update")
    public ResponseEntity<Void> updatePhysicalAttributes(@RequestBody UpdatePhysicalAttributesResource resource) {
        this.physicalAttributesService.updatePhysicalAttributes(resource);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
