package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "logoDay")
@Getter
@Setter
public class LogoDay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer day;

    private Boolean isFastest;

    private Integer logoPlayerId;

    private Integer points;

    private Integer seasonId;
}
