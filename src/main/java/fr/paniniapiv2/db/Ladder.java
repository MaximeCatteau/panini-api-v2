package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "ladder")
@Getter
@Setter
public class Ladder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int cardCount;

    private String playerUsername;
}
