package fr.paniniapiv2.rp.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTechnicalFieldPlayerAttributesResource {
    private int footballPlayerId;
    private String attributeUpdated;
    private int centers;
    private int corners;
    private int dribbles;
    private int headers;
    private int passes;
    private int tackles;
    private int longShots;
    private int ballControl;
    private int freeKicks;
    private int finition;
    private int marking;
    private int penalty;
    private int technique;
    private int longThrows;
}
