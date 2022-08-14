package fr.paniniapiv2.rp.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "characterStatistics")
@Getter
@Setter
public class CharacterStatistics {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int ambition;

    private int versatility;

    private int fairPlay;

    private int loyalty;

    private int regularity;

    private int importantGames;

    private int controversy;

    private int pressure;

    private int professionalism;

    private int temperament;

    private int adaptability;

    private int violence;

    private int fragility;
}
