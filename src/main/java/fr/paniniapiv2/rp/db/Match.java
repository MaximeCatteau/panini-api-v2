package fr.paniniapiv2.rp.db;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import fr.paniniapiv2.rp.enums.EMatchIssue;
import fr.paniniapiv2.rp.enums.EMatchStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "match")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    @OneToOne
    public Club home = null;

    @OneToOne
    public Club away = null;

    public EMatchIssue issue = EMatchIssue.N;

    public int scoreHome = 0;

    public int scoreAway = 0;

    @ManyToMany
    public List<FootballPlayer> strikers = new ArrayList<>();

    @ManyToMany
    public List<FootballPlayer> passers = new ArrayList<>();

    @OneToOne
    public FootballPlayer manOfTheMatch = null;

    @ManyToMany
    public List<MatchNote> notes = new ArrayList<>();

    public String videoLink = "";

    public EMatchStatus matchStatus = EMatchStatus.COMING;

    public String date = "";

    @OneToOne
    public Competition competition;
}
