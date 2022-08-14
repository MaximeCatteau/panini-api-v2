package fr.paniniapiv2.rp.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "physicalAttributes")
@Getter
@Setter
public class PhysicalAttributes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int acceleration;

    private int agility;

    private int verticalExtension;

    private int endurance;

    private int equilibrium;

    private int power;

    private int naturalPhysicAbilities;

    private int speed;
}
