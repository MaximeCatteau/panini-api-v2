package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TradeResource {
    private int tradeId;
    private String transmitterName;
    private String recipientName;
    private String tradeStatus;
    private String transmitterCardLabel;
    private String transmitterCollectionLabel;
    private String recipientCardLabel;
    private String recipientCollectionLabel;
    private int propositionCount;
}
