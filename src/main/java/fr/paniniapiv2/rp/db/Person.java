package fr.paniniapiv2.rp.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "person")
@Getter
@Setter
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;

    private String lastName;

    private String birthday;

    @OneToOne
    private Club club;

    @OneToOne
    private Nationality mainNationality;

    @OneToOne
    private Nationality secondNationality;

    @OneToMany
    private List<Person> favoritePersons;

    @OneToMany
    private List<Club> favoriteClubs;

    private String functionName;

    private float budget;
}
