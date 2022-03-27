package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "whoAmIDay")
@Getter
@Setter
public class WhoAmIDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer day;

    private Integer whoAmIPlayerId;

    private Integer points;

    private Integer seasonId;
}
