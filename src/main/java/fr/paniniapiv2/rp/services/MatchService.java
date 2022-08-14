package fr.paniniapiv2.rp.services;

import fr.paniniapiv2.rp.db.Club;
import fr.paniniapiv2.rp.db.FootballPlayer;
import fr.paniniapiv2.rp.db.Match;
import fr.paniniapiv2.rp.db.MatchNote;
import fr.paniniapiv2.rp.enums.EMatchIssue;
import fr.paniniapiv2.rp.enums.EMatchStatus;
import fr.paniniapiv2.rp.repositories.ClubRepository;
import fr.paniniapiv2.rp.repositories.FootballPlayerRepository;
import fr.paniniapiv2.rp.repositories.MatchNoteRepository;
import fr.paniniapiv2.rp.repositories.MatchRepository;
import fr.paniniapiv2.rp.resources.CreateMatchResource;
import fr.paniniapiv2.rp.resources.FootballPlayerResource;
import fr.paniniapiv2.rp.resources.MatchNoteResource;
import fr.paniniapiv2.rp.resources.PlayMatchResource;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
public class MatchService {
    @Autowired
    MatchRepository matchRepository;

    @Autowired
    ClubRepository clubRepository;

    @Autowired
    FootballPlayerRepository footballPlayerRepository;

    @Autowired
    MatchNoteRepository matchNoteRepository;

    /**
     * DISCORD
     * @param resource
     * @return
     */
    DiscordApi api = new DiscordApiBuilder()
            .setToken("OTEwMDg2MTQyNTgwMzY3Mzgw.YZNtxA.qZBFOb7Ro2yct4Ddv_AQAEYTs50")
            .login().join();

    public MatchService(MatchRepository matchRepository, ClubRepository clubRepository, FootballPlayerRepository footballPlayerRepository,
                        MatchNoteRepository matchNoteRepository) {
        this.matchRepository = matchRepository;
        this.clubRepository = clubRepository;
        this.footballPlayerRepository = footballPlayerRepository;
        this.matchNoteRepository = matchNoteRepository;
    }
    public Match createMatch(Match match) {
        this.matchRepository.save(match);

        return match;
    }

    public List<Match> getAllMatchesForClub(Club club) {
        return this.matchRepository.getAllMatchByClub(club.getId());
    }

    public Match getMatchById(int id) {
        return this.matchRepository.findById(id).orElseThrow();
    }

    public Match playMatch(PlayMatchResource playMatchResource) throws IOException, URISyntaxException {
        Match match = this.matchRepository.getById(playMatchResource.getId());

        match.setMatchStatus(EMatchStatus.FINISHED);
        match.setScoreHome(playMatchResource.getScoreHome());
        match.setScoreAway(playMatchResource.getScoreAway());
        match.setIssue(EMatchIssue.valueOf(playMatchResource.getIssue()));
        match.setManOfTheMatch(this.footballPlayerRepository.getById(playMatchResource.getManOfTheMatch().getId()));

        List<FootballPlayer> strikers = new ArrayList<>();

        for (FootballPlayerResource fpr : playMatchResource.getStrikers()) {
            strikers.add(this.footballPlayerRepository.getById(fpr.getId()));
        }

        match.setStrikers(strikers);

        List<FootballPlayer> passers = new ArrayList<>();

        for (FootballPlayerResource fpr : playMatchResource.getPassers()) {
            passers.add(this.footballPlayerRepository.getById(fpr.getId()));
        }

        match.setPassers(passers);

        List<MatchNote> matchNotes = new ArrayList<>();

        for (MatchNoteResource mnr : playMatchResource.getNotes()) {
            MatchNote matchNote = new MatchNote();

            matchNote.setNote(mnr.getMatchNote());
            matchNote.setFootballPlayer(this.footballPlayerRepository.getById(mnr.getFootballPlayerId()));

            this.matchNoteRepository.save(matchNote);

            this.processXp(mnr);

            matchNotes.add(matchNote);
        }

        match.setNotes(matchNotes);

        return this.matchRepository.save(match);
    }

    private void processXp(MatchNoteResource mnr) throws IOException, URISyntaxException {
        Map<Integer, Integer> levelTable = this.loadXpPerLevel();
        FootballPlayer fpr = this.footballPlayerRepository.getById(mnr.getFootballPlayerId());
        Club club = fpr.getClub();

        int oldXp = fpr.getExperience();

        int xpGained = Math.round(mnr.getMatchNote() * (10 + club.getInfrastructures().getTrainingCenter() - 1));

        CompletableFuture<User> user = api.getUserById(fpr.getOwner().getDiscordId());

        if (willUp(levelTable.get(fpr.getLevel()+1) - oldXp, xpGained)) {
            fpr.setLevel(fpr.getLevel() + 1);
            fpr.setPointsToSet(fpr.getPointsToSet() + 1);
            try {
                PrivateChannel pc = user.get().openPrivateChannel().get();
                pc.sendMessage("**RP BINOUZE**\n\n" +
                        "Félicitations " + fpr.getFirstName() + " " + fpr.getLastName() + " !\n\n" +
                        "Suite à votre dernier match, vous avez gagné **" + xpGained + "** points d'expérience et vous " +
                        "montez au niveau **" + fpr.getLevel() + "** ! Vous avez donc un point d'attribut à répartir parmi " +
                        "les différents attributs (physique, mental, technique) !\n\n" +
                        "Bravo pour votre performance !");
            } catch(Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                PrivateChannel pc = user.get().openPrivateChannel().get();
                pc.sendMessage("**RP BINOUZE**\n\n" +
                        "Félicitations " + fpr.getFirstName() + " " + fpr.getLastName() + " !\n\n" +
                        "Suite à votre dernier match, vous avez gagné **" + xpGained + "** points d'expérience !\n\n" +
                        "Bravo pour votre performance !");
            } catch(Exception e) {
                e.printStackTrace();
            }
        }

        fpr.setExperience(oldXp + xpGained);

        this.footballPlayerRepository.save(fpr);
    }

    private Map<Integer, Integer> loadXpPerLevel() throws IOException, URISyntaxException {
        Map<Integer, Integer> xpPerLevel = new HashMap<>();

        ClassLoader classLoader = getClass().getClassLoader();
        URL resource = classLoader.getResource("xp_steps.txt");
        File file = new File(resource.toURI());

        int level = 1;

        List<String> lines = Files.lines(file.toPath()).collect(Collectors.toList());

        for (String line : lines) {
            xpPerLevel.put(level, Integer.valueOf(line));
            level++;
        }

        return xpPerLevel;
    }

    private boolean willUp(int necessaryXp, int xpGained) {
        System.out.println("NECESSARY : " + necessaryXp);
        System.out.println("GAINED : " + xpGained);
        return xpGained >= necessaryXp;
    }
}
