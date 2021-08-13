package fr.paniniapiv2.db;

import fr.paniniapiv2.enums.CardRarety;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "card")
@Getter
@Setter
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String label;

    private String imageUrl;

    private Integer idInCollection;

    private Integer collectionId;

    private String cardRarity;
}
