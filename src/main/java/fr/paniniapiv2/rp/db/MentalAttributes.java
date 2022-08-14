package fr.paniniapiv2.rp.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "mentalAttributes")
@Getter
@Setter
public class MentalAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int aggressiveness;

    private int anticipation;

    private int ballCalls;

    private int concentration;

    private int bravery;

    private int decisionMaking;

    private int determination;

    private int inspiration;

    private int collectiveGame;

    private int leadership;

    private int placement;

    private int coldBlood;

    private int gameView;

    private int gameVolume;
}
