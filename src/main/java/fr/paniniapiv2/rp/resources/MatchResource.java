package fr.paniniapiv2.rp.resources;

import fr.paniniapiv2.rp.db.FootballPlayer;
import fr.paniniapiv2.rp.db.Match;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class MatchResource {
    private int id;

    private ClubResource home;

    private ClubResource away;

    private int scoreHome;

    private int scoreAway;

    private String issue;

    private List<FootballPlayerResource> strikers;

    private List<FootballPlayerResource> passers;

    private String date;

    private String status;

    public static MatchResource fromMatch(Match match) {
        MatchResource matchResource = new MatchResource();

        matchResource.setId(match.getId());
        matchResource.setHome(ClubResource.fromClub(match.getHome()));
        matchResource.setAway(ClubResource.fromClub(match.getAway()));
        matchResource.setScoreHome(match.getScoreHome());
        matchResource.setScoreAway(match.getScoreAway());
        matchResource.setIssue(match.getIssue().name());
        matchResource.setDate(match.getDate());
        matchResource.setStatus(match.getMatchStatus().name());

        List<FootballPlayer> strikers = match.getStrikers();
        List<FootballPlayerResource> strikersResource = new ArrayList<>();

        for (FootballPlayer fp : strikers) {
            FootballPlayerResource fpr = new FootballPlayerResource();
            strikersResource.add(fpr.fromFootballPlayer(fp));
        }

        matchResource.setStrikers(strikersResource);

        List<FootballPlayer> passers = match.getPassers();
        List<FootballPlayerResource> passersResource = new ArrayList<>();

        for (FootballPlayer fp : passers) {
            FootballPlayerResource fpr = new FootballPlayerResource();
            passersResource.add(fpr.fromFootballPlayer(fp));
        }

        matchResource.setPassers(passersResource);

        return matchResource;
    }
}
