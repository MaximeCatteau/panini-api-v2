package fr.paniniapiv2.rp.resources;

import fr.paniniapiv2.rp.db.Match;
import fr.paniniapiv2.rp.db.MatchNote;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class PlayMatchResource {
    private int id;

    private int scoreHome;

    private int scoreAway;

    private String issue;

    private List<FootballPlayerResource> strikers;

    private List<FootballPlayerResource> passers;

    private FootballPlayerResource manOfTheMatch;

    private List<MatchNoteResource> notes;

    public static PlayMatchResource fromMatch(Match m) {
        PlayMatchResource playMatchResource = new PlayMatchResource();

        playMatchResource.setId(m.getId());
        playMatchResource.setScoreHome(m.getScoreHome());
        playMatchResource.setScoreAway(m.getScoreAway());
        playMatchResource.setManOfTheMatch(FootballPlayerResource.fromFootballPlayer(m.getManOfTheMatch()));

        List<MatchNoteResource> matchNoteResources = new ArrayList<>();

        for (MatchNote mn : m.getNotes()) {
            matchNoteResources.add(MatchNoteResource.fromMatchNote(mn));
        }

        playMatchResource.setNotes(matchNoteResources);

        return playMatchResource;
    }
}
