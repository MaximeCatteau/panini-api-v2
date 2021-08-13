package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "playerCard")
@Getter
@Setter
public class PlayerCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long playerId;

    private Integer cardId;

    private Integer quantity = 1;
}
