package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "playerTitle")
@Getter
@Setter
public class PlayerTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long playerId;

    private Integer logoPlayerId;

    private Boolean selected;

    private Integer titleId;
}
