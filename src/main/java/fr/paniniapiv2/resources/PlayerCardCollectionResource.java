package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerCardCollectionResource {
    private int cardId;
    private String cardLabel;
    private int cardQuantity;
    private String collectionLabel;
}
