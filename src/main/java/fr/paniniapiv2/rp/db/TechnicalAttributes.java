package fr.paniniapiv2.rp.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "technicalAttributes")
@Getter
@Setter
public class TechnicalAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int ballControl;

    private int passes;
}
