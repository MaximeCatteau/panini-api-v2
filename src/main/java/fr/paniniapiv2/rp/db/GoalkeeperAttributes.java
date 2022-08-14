package fr.paniniapiv2.rp.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "goalkeeperAttributes")
@Getter
@Setter
public class GoalkeeperAttributes extends TechnicalAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int communication;

    private int fistClearances;

    private int clearances;

    private int extension;

    private int excentricity;

    private int ballCatches;

    private int reflexes;

    private int handThrows;

    private int inboxExits;

    private int inFeetExits;

    private int oneVersusOne;
}
