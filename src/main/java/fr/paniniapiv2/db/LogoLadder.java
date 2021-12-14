package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "logoLadder")
@Getter
@Setter
public class LogoLadder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer seasonId;

    private Integer league;

    private Integer logoPlayerId;

    private Integer totalPoints;

    private Integer dayPoints;

    private Integer totalGuessed;

    private Integer streak;

    private Integer fastest;
}
