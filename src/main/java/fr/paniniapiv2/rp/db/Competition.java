package fr.paniniapiv2.rp.db;

import fr.paniniapiv2.rp.enums.ECompetitionStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "competition")
@Getter
@Setter
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private int year;

    @OneToOne
    private Club winner;

    private ECompetitionStatus competitionStatus;
}
