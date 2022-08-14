package fr.paniniapiv2.rp.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePhysicalAttributesResource {
    private int footballPlayerId;
    private String attributeUpdated;
    private int acceleration;
    private int verticalExtension;
    private int equilibrium;
    private int naturalPhysicAbilities;
    private int agility;
    private int endurance;
    private int power;
    private int speed;
}
