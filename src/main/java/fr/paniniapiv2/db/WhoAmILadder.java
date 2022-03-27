package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "whoAmILadder")
@Getter
@Setter
public class WhoAmILadder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer seasonId;

    private Integer whoAmIPlayerId;

    private Integer totalPoints;

    private Integer dayPoints;

    private Integer totalGuessed;

    private Integer streak;
}
