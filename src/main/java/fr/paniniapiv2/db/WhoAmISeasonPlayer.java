package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "whoAmISeasonPlayer")
@Getter
@Setter
public class WhoAmISeasonPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer whoAmISeasonId;

    private Integer whoAmIPlayerId;
}
