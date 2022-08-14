package fr.paniniapiv2.rp.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "nationality")
@Getter
@Setter
public class Nationality {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String country;

    private String flagIndicator;
}
