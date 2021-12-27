package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CollectionLadderResource {
    Long playerId;
    int cardCount;
    String playerName;
    String titleSelected;
    String titleSelectedColor;
}
