package fr.paniniapiv2.rp.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "infrastructures")
@Getter
@Setter
public class Infrastructures {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    private int formationCenter = 1;

    private int trainingCenter = 1;

    private int formationTrainingCenter = 1;

    private int vipBoxes = 0;
}
