package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PlayerCareerResource {
    int totalCards;
    int totalCardsToGet;
    int collectionsOwned;
    int totalCollectionsToGet;
    int moneySpent;
    int collectionsCompleted;
    int tradesCompleted;
}
