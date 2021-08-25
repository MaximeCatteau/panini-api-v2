package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "playerCareer")
@Getter
@Setter
public class PlayerCareer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long playerId;

    private int totalCard = 0;

    private int collections = 0;

    private int collectionsCompleted = 0;

    private int missingCards = 0;

    private boolean isLogoGuesser = false;

    private int positionOnLadder = 0;

    private int cashSpent = 0;

    private int tradeCompleted = 0;
}
