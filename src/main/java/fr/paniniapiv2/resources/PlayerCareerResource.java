package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerCareerResource {
    Long playerId;
    int totalCards;
    int totalCardsToGet;
    int collectionsOwned;
    int totalCollectionsToGet;
    int moneySpent;
    int collectionsCompleted;
    int tradesCompleted;
}
