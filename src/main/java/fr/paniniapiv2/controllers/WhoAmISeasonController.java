package fr.paniniapiv2.controllers;

import fr.paniniapiv2.db.*;
import fr.paniniapiv2.repositories.*;
import fr.paniniapiv2.resources.*;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@EnableScheduling
public class WhoAmISeasonController {
    @Autowired
    WhoAmISeasonRepository whoAmISeasonRepository;

    @Autowired
    WhoAmISeasonPlayerRepository whoAmISeasonPlayerRepository;

    @Autowired
    WhoAmIPlayerRepository whoAmIPlayerRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired
    WhoAmILadderRepository whoAmILadderRepository;

    @Autowired
    WhoAmIDayRepository whoAmIDayRepository;

    @Autowired
    PlayerTitleRepository playerTitleRepository;

    @Autowired
    TitleRepository titleRepository;

    /**
     * DISCORD
     * @param resource
     * @return
     */
    DiscordApi api = new DiscordApiBuilder()
            .setToken("OTEwMDg2MTQyNTgwMzY3Mzgw.YZNtxA.qZBFOb7Ro2yct4Ddv_AQAEYTs50")
            .login().join();

    @CrossOrigin
    @GetMapping("/who-am-i/seasons")
    public ResponseEntity<List<WhoAmISeason>> getAllSeasons() {
        return new ResponseEntity<>(this.whoAmISeasonRepository.findAll(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/who-am-i/season")
    public ResponseEntity<WhoAmISeason> getSeasonByID(@RequestParam String seasonId) {
        return new ResponseEntity<>(this.whoAmISeasonRepository.findById(Integer.parseInt(seasonId)).orElseThrow(), HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/who-am-i/seasons/players/count")
    public ResponseEntity<Integer> getNumberOfPlayerForSeason(@RequestParam Integer seasonId) {
        return new ResponseEntity<>(this.whoAmISeasonPlayerRepository.countByWhoAmISeasonId(seasonId), HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/who-am-i/seasons/create")
    public ResponseEntity<WhoAmISeason> createSeason(@RequestBody WhoAmISeasonResource whoAmISeasonResource) {
        WhoAmISeason whoAmISeason = new WhoAmISeason();

        whoAmISeason.setName(whoAmISeasonResource.getName());
        whoAmISeason.setCurrentDay(0);
        whoAmISeason.setTotalDays(whoAmISeasonResource.getTotalDays());
        whoAmISeason.setStatus("CREATED");

        this.whoAmISeasonRepository.save(whoAmISeason);

        return new ResponseEntity<>(whoAmISeason, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/who-am-i/season/player/add")
    public ResponseEntity<WhoAmISeasonPlayer> addSeasonPlayer(@RequestParam String discordId, @RequestParam String seasonId) {
        Title title = this.titleRepository.findByLabel("Player Seekr");

        // Check si le WhoAmIPlayer existe déjà et ajouter si besoin et ajouter un titre
        WhoAmIPlayer player = this.whoAmIPlayerRepository.findWhoAmIPlayerByDiscordId(discordId);

        WhoAmISeasonPlayer whoAmISeasonPlayer = new WhoAmISeasonPlayer();

        PlayerTitle pt = new PlayerTitle();

        if (player == null) {
            player = new WhoAmIPlayer();
            player.setDiscordId(discordId);
            pt.setTitleId(title.getId());
            pt.setSelected(true);

            if (playerRepository.existsByDiscordId(discordId)) {
                Player p = this.playerRepository.findByDiscordId(discordId).orElseThrow();

                player.setPlayerIdAssociated(p.getId());
                pt.setPlayerId(p.getId());

                if (this.playerTitleRepository.existsByPlayerId(p.getId())) {
                    pt.setSelected(false);
                }
            }

            whoAmISeasonPlayer.setWhoAmISeasonId(Integer.parseInt(seasonId));

            this.whoAmIPlayerRepository.save(player);

            WhoAmIPlayer playerCreated = this.whoAmIPlayerRepository.findWhoAmIPlayerByDiscordId(discordId);
            whoAmISeasonPlayer.setWhoAmIPlayerId(playerCreated.getId());

            pt.setPlayerSeekerId(playerCreated.getId());
            this.playerTitleRepository.save(pt);

            this.whoAmISeasonPlayerRepository.save(whoAmISeasonPlayer);
        } else {
            if (this.whoAmISeasonPlayerRepository.existsByWhoAmIPlayerId(player.getId())) {
                return new ResponseEntity<>(HttpStatus.IM_USED);
            } else {
                whoAmISeasonPlayer.setWhoAmISeasonId(Integer.parseInt(seasonId));
                whoAmISeasonPlayer.setWhoAmIPlayerId(player.getId());
            }
        }

        // Ajouter au Ladder
        WhoAmILadder whoAmILadder = new WhoAmILadder();

        whoAmILadder.setSeasonId(Integer.parseInt(seasonId));
        whoAmILadder.setDayPoints(0);
        whoAmILadder.setWhoAmIPlayerId(whoAmIPlayerRepository.findWhoAmIPlayerByDiscordId(discordId).getId());
        whoAmILadder.setStreak(0);
        whoAmILadder.setTotalGuessed(0);
        whoAmILadder.setTotalPoints(0);

        this.whoAmILadderRepository.save(whoAmILadder);

        return new ResponseEntity<>(whoAmISeasonPlayer, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/who-am-i/ladder")
    public ResponseEntity<List<WhoAmILadderResource>> getWhoAmILadder(@RequestParam String seasonId) throws ExecutionException, InterruptedException {
        List<WhoAmILadder> whoAmILadderList = this.whoAmILadderRepository.findBySeasonId(Integer.parseInt(seasonId));

        List<WhoAmILadderResource> whoAmILadderResourceList = new ArrayList<>();

        for (WhoAmILadder whoAmILadder : whoAmILadderList) {
            WhoAmILadderResource whoAmILadderResource = new WhoAmILadderResource();

            whoAmILadderResource.setId(whoAmILadder.getId());
            whoAmILadderResource.setSeasonId(whoAmILadder.getSeasonId());
            whoAmILadderResource.setDayPoints(whoAmILadder.getDayPoints());
            whoAmILadderResource.setStreak(whoAmILadder.getStreak());
            whoAmILadderResource.setTotalGuessed(whoAmILadder.getTotalGuessed());
            whoAmILadderResource.setTotalPoints(whoAmILadder.getTotalPoints());

            WhoAmIPlayer whoAmIPlayer = this.whoAmIPlayerRepository.getById(whoAmILadder.getWhoAmIPlayerId());

            String playerName = this.api.getUserById(whoAmIPlayer.getDiscordId()).get().getName();
            String avatar = this.api.getUserById(whoAmIPlayer.getDiscordId()).get().getAvatar().getUrl().toString();

            whoAmILadderResource.setPlayerName(playerName);
            whoAmILadderResource.setPlayerAvatar(avatar);

            PlayerTitle playerTitle = this.playerTitleRepository.getSelectedTitleByPlayerSeekerId(whoAmIPlayer.getId());

            if (playerTitle != null) {
                Title title = this.titleRepository.getById(playerTitle.getTitleId());

                whoAmILadderResource.setTitleLabel(title.getLabel());
                whoAmILadderResource.setTitleColor(title.getColor());
            }

            whoAmILadderResourceList.add(whoAmILadderResource);
        }

        return new ResponseEntity<>(whoAmILadderResourceList, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/who-am-i/day")
    public ResponseEntity<WhoAmIDay> postNewDay(@RequestParam String token, @RequestBody NewWhoAmIDayResource newWhoAmIDayResource) throws ExecutionException, InterruptedException {
        Player player = this.playerRepository.findByToken(token).orElseThrow();

        if (!player.getRole().equals("ADMIN") && !player.getRole().equals("WHO_AM_I_ORGANIZER")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // ENREGISTRER LES MODIFS SAISON
        WhoAmISeason season = this.whoAmISeasonRepository.findCurrentSeason();
        List<WhoAmIDay> previousDay = this.whoAmIDayRepository.findBySeasonIdAndDay(season.getId(), season.getCurrentDay());
        int newDay = season.getCurrentDay() + 1;
        season.setCurrentDay(newDay);

        this.whoAmISeasonRepository.save(season);

        // AJOUTER LES DONNEES DANS WHO AM I DAY
        // -- LIGUE 1
        for (WhoAmIPlayerDataResource lpd : newWhoAmIDayResource.getWhoAmIPlayerResourceList()) {
            WhoAmIPlayer whoAmIPlayer = this.findByDiscordName(lpd.getWhoAmIPlayerName());

            if (whoAmIPlayer == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            WhoAmIDay whoAmIDay = new WhoAmIDay();

            whoAmIDay.setDay(newDay);
            whoAmIDay.setWhoAmIPlayerId(whoAmIPlayer.getId());
            whoAmIDay.setPoints(lpd.getPoints());
            whoAmIDay.setSeasonId(season.getId());

            this.processLadder(whoAmIDay);
            this.whoAmIDayRepository.save(whoAmIDay);
        }

        // Process streaks
        List<WhoAmIDay> currentDay = this.whoAmIDayRepository.findBySeasonIdAndDay(season.getId(), newDay);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/who-am-i/daily")
    public ResponseEntity<List<DailyWhoAmILadderResource>> getDailyLadder(@RequestParam String token, @RequestParam int seasonId) throws ExecutionException, InterruptedException {
        Player player = this.playerRepository.findByToken(token).orElseThrow();

        WhoAmISeason whoAmISeason = this.whoAmISeasonRepository.findCurrentSeason();

        int currentDayInSeason = whoAmISeason.getCurrentDay();

        List<DailyWhoAmILadderResource> dailyWhoAmILadderResourceList = new ArrayList<>();

        for (int i = 1; i <= currentDayInSeason; i++) {
            List<WhoAmIDay> seasonAndDayWhoAmI = this.whoAmIDayRepository.findBySeasonIdAndDay(seasonId, i);
            DailyWhoAmILadderResource dailyWhoAmILadderResource = new DailyWhoAmILadderResource();
            List<WhoAmIDayResource> league = new ArrayList<>();

            for (WhoAmIDay ld : seasonAndDayWhoAmI) {
                WhoAmIDayResource whoAmIDayResource = new WhoAmIDayResource();

                WhoAmIPlayer whoAmIPlayer = this.whoAmIPlayerRepository.findById(ld.getWhoAmIPlayerId()).orElseThrow();

                whoAmIDayResource.setWhoAmIPlayerName(this.api.getUserById(whoAmIPlayer.getDiscordId()).get().getName());
                whoAmIDayResource.setPoints(ld.getPoints());

                league.add(whoAmIDayResource);
            }

            dailyWhoAmILadderResource.setDay(i);
            dailyWhoAmILadderResource.setLeague(league);
            dailyWhoAmILadderResourceList.add(dailyWhoAmILadderResource);
        }

        dailyWhoAmILadderResourceList.sort(Comparator.comparing(DailyWhoAmILadderResource::getDay).reversed());

        return new ResponseEntity<>(dailyWhoAmILadderResourceList, HttpStatus.OK);
    }

    private void processLadder(WhoAmIDay whoAmIDay) {
        WhoAmILadder whoAmILadder = this.whoAmILadderRepository.findByWhoAmIPlayerId(whoAmIDay.getWhoAmIPlayerId());

        int newPoints = whoAmILadder.getTotalPoints() + whoAmIDay.getPoints();

        whoAmILadder.setDayPoints(whoAmIDay.getPoints());
        whoAmILadder.setTotalGuessed(whoAmILadder.getTotalGuessed() + 1);
        whoAmILadder.setTotalPoints(newPoints);

        this.whoAmILadderRepository.save(whoAmILadder);
    }

    private WhoAmIPlayer findByDiscordName(String name) throws ExecutionException, InterruptedException {
        List<WhoAmIPlayer> whoAmIPlayerList = this.whoAmIPlayerRepository.findAll();

        for (WhoAmIPlayer player : whoAmIPlayerList) {
            String discordName = this.api.getUserById(player.getDiscordId()).get().getName();

            if (discordName.equals(name)) {
                return player;
            }
        }

        return null;
    }

}
