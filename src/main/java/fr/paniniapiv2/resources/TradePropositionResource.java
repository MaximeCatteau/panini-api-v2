package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradePropositionResource {
    private int tradeId;
    private int tradePropositionId;
    private int cardConcernedId;
    private String cardConcernedLabel;
    private String cardConcernedCollectionLabel;
    private int cardProposedId;
    private String cardProposedLabel;
    private String cardProposedCollectionLabel;
    private String propositionEmitter;
}
