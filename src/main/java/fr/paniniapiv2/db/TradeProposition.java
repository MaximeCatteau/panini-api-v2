package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "tradePropositions")
@Getter
@Setter
public class TradeProposition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer recipientId;

    private Integer cardId;
}