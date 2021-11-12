package fr.paniniapiv2.resources;

import fr.paniniapiv2.db.Card;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerCardResource {
    private Card card;
    private int cardQuantity;
}
