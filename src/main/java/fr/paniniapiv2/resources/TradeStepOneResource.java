package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeStepOneResource {
    private int tradeId;
    private Long transmitterId;
    private String transmitterName;
    private int cardId;
    private String cardLabel;
    private String collectionLabel;
    private int propositionCount;
}
