package fr.paniniapiv2.rp.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateTechnicalGoalKeeperAttributesResource {
    private int footballPlayerId;
    private String attributeUpdated;
    private int communication;
    private int ballControl;
    private int fistClearances;
    private int clearances;
    private int extension;
    private int excentricity;
    private int passes;
    private int ballCatches;
    private int reflexes;
    private int handThrows;
    private int inboxExits;
    private int inFeetExits;
    private int oneVersusOne;
}
