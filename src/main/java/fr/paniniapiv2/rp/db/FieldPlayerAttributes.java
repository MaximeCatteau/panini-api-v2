package fr.paniniapiv2.rp.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "fieldPlayerAttributes")
@Getter
@Setter
public class FieldPlayerAttributes extends TechnicalAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int centers;

    private int corners;

    private int freeKicks;

    private int dribbles;

    private int finition;

    private int headers;

    private int marking;

    private int penalty;

    private int tackles;

    private int technique;

    private int longShots;

    private int longThrows;
}
