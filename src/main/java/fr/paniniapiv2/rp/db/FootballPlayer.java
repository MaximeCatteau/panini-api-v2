package fr.paniniapiv2.rp.db;

import fr.paniniapiv2.db.Player;
import fr.paniniapiv2.rp.enums.EFootPreference;
import fr.paniniapiv2.rp.enums.EPosition;
import fr.paniniapiv2.rp.enums.EPost;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "footballPlayer")
@Getter
@Setter
public class FootballPlayer extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private EPost post;

    private EPosition position;

    private EFootPreference footPreference;

    private int level;

    private int experience;

    private int pointsToSet;

    @OneToMany
    private List<Trait> traits;

    @OneToMany
    private List<Trait> selectedTraits;

    @OneToOne
    private CharacterStatistics characterStatistics;

    @OneToOne
    private PhysicalAttributes physicalAttributes;

    @OneToOne
    private MentalAttributes mentalAttributes;

    @OneToOne
    private TechnicalAttributes technicalAttributes;

    private int size;

    private int weight;

    private int number;

    private String avatar;

    @OneToOne
    private Player owner;

    private String ownerDiscordId;
}
