package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerProgressOnCollectionResource {
    private String collectionLabel;
    private int playerCardCount;
    private int collectionCardCount;
    private int playerCardCountPercent;
}
