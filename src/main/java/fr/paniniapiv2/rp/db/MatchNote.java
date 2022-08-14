package fr.paniniapiv2.rp.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "matchNote")
@Getter
@Setter
public class MatchNote {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    FootballPlayer footballPlayer;

    private float note;
}
