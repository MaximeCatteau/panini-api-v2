package fr.paniniapiv2.controllers;

import fr.paniniapiv2.db.*;
import fr.paniniapiv2.repositories.*;
import fr.paniniapiv2.resources.*;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class LogoController {
    @Autowired
    private LogoLadderRepository logoLadderRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private LogoPlayerRepository logoPlayerRepository;

    @Autowired
    private LogoDayRepository logoDayRepository;

    @Autowired
    private LogoSeasonRepository logoSeasonRepository;

    @Autowired
    private PlayerTitleRepository playerTitleRepository;

    @Autowired
    private TitleRepository titleRepository;

    @CrossOrigin
    @GetMapping("/logo/league/1")
    public ResponseEntity<List<LogoLadderResource>> getLeague1Ladder(@RequestParam String token, @RequestParam int seasonId) {
        Player p = this.playerRepository.findByToken(token).orElseThrow();

        LogoSeason currentSeason = this.logoSeasonRepository.getById(seasonId);
        List<LogoLadder> rawLadder = this.logoLadderRepository.getLeague1Ladder(currentSeason.getId());
        List<LogoLadderResource> resource = new ArrayList<>();

        for (LogoLadder ll : rawLadder) {
            LogoLadderResource llr = new LogoLadderResource();

            llr.setLogoPlayerName(this.logoPlayerRepository.findById(ll.getLogoPlayerId()).orElseThrow().getLogoPlayerName());
            llr.setTotalPoints(ll.getTotalPoints());
            llr.setDayPoints(ll.getDayPoints());
            llr.setLogoGuessed(ll.getTotalGuessed());
            llr.setStreak(ll.getStreak());
            llr.setFastest(ll.getFastest());

            PlayerTitle titleSelected = this.playerTitleRepository.getSelectedTitleByLogoPlayerId(ll.getLogoPlayerId());

            if (titleSelected != null) {
                Title t = this.titleRepository.getById(titleSelected.getTitleId());
                llr.setPlayerTitle(t.getLabel());
                llr.setColor(t.getColor());
            }

            resource.add(llr);
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/logo/league/2")
    public ResponseEntity<List<LogoLadderResource>> getLeague2Ladder(@RequestParam String token, @RequestParam int seasonId) {
        Player p = this.playerRepository.findByToken(token).orElseThrow();

        LogoSeason currentSeason = this.logoSeasonRepository.getById(seasonId);
        List<LogoLadder> rawLadder = this.logoLadderRepository.getLeague2Ladder(currentSeason.getId());
        List<LogoLadderResource> resource = new ArrayList<>();

        for (LogoLadder ll : rawLadder) {
            LogoLadderResource llr = new LogoLadderResource();

            llr.setLogoPlayerName(this.logoPlayerRepository.findById(ll.getLogoPlayerId()).orElseThrow().getLogoPlayerName());
            llr.setTotalPoints(ll.getTotalPoints());
            llr.setDayPoints(ll.getDayPoints());
            llr.setLogoGuessed(ll.getTotalGuessed());
            llr.setStreak(ll.getStreak());
            llr.setFastest(ll.getFastest());

            PlayerTitle titleSelected = this.playerTitleRepository.getSelectedTitleByLogoPlayerId(ll.getLogoPlayerId());

            if (titleSelected != null) {
                Title t = this.titleRepository.getById(titleSelected.getTitleId());
                llr.setPlayerTitle(t.getLabel());
                llr.setColor(t.getColor());
            }

            resource.add(llr);
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/logo/league/2/a")
    public ResponseEntity<List<LogoLadderResource>> getLeague2aLadder(@RequestParam String token, @RequestParam int seasonId) {
        Player p = this.playerRepository.findByToken(token).orElseThrow();

        LogoSeason currentSeason = this.logoSeasonRepository.getById(seasonId);
        List<LogoLadder> rawLadder = this.logoLadderRepository.getLeague2aLadder(currentSeason.getId());
        List<LogoLadderResource> resource = new ArrayList<>();

        for (LogoLadder ll : rawLadder) {
            LogoLadderResource llr = new LogoLadderResource();

            llr.setLogoPlayerName(this.logoPlayerRepository.findById(ll.getLogoPlayerId()).orElseThrow().getLogoPlayerName());
            llr.setTotalPoints(ll.getTotalPoints());
            llr.setDayPoints(ll.getDayPoints());
            llr.setLogoGuessed(ll.getTotalGuessed());
            llr.setStreak(ll.getStreak());
            llr.setFastest(ll.getFastest());

            PlayerTitle titleSelected = this.playerTitleRepository.getSelectedTitleByLogoPlayerId(ll.getLogoPlayerId());

            if (titleSelected != null) {
                Title t = this.titleRepository.getById(titleSelected.getTitleId());
                llr.setPlayerTitle(t.getLabel());
                llr.setColor(t.getColor());
            }

            resource.add(llr);
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/logo/league/2/b")
    public ResponseEntity<List<LogoLadderResource>> getLeague2bLadder(@RequestParam String token, @RequestParam int seasonId) {
        Player p = this.playerRepository.findByToken(token).orElseThrow();

        LogoSeason currentSeason = this.logoSeasonRepository.getById(seasonId);
        List<LogoLadder> rawLadder = this.logoLadderRepository.getLeague2bLadder(currentSeason.getId());
        List<LogoLadderResource> resource = new ArrayList<>();

        for (LogoLadder ll : rawLadder) {
            LogoLadderResource llr = new LogoLadderResource();

            llr.setLogoPlayerName(this.logoPlayerRepository.findById(ll.getLogoPlayerId()).orElseThrow().getLogoPlayerName());
            llr.setTotalPoints(ll.getTotalPoints());
            llr.setDayPoints(ll.getDayPoints());
            llr.setLogoGuessed(ll.getTotalGuessed());
            llr.setStreak(ll.getStreak());
            llr.setFastest(ll.getFastest());

            PlayerTitle titleSelected = this.playerTitleRepository.getSelectedTitleByLogoPlayerId(ll.getLogoPlayerId());

            if (titleSelected != null) {
                Title t = this.titleRepository.getById(titleSelected.getTitleId());
                llr.setPlayerTitle(t.getLabel());
                llr.setColor(t.getColor());
            }

            resource.add(llr);
        }

        return new ResponseEntity<>(resource, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/logo/daily")
    public ResponseEntity<List<DailyLogoLadderResource>> getDailyLadders(@RequestParam String token, @RequestParam int seasonId) {
        Player player = this.playerRepository.findByToken(token).orElseThrow();

        LogoSeason logoSeason = this.logoSeasonRepository.findCurrentSeason();

        int currentDayInSeason = logoSeason.getCurrentDay();

        List<DailyLogoLadderResource> dailyLogoLadderResourceList = new ArrayList<>();

        for (int i = 1; i <= currentDayInSeason; i++) {
            List<LogoDay> seasonAndDayLogo = this.logoDayRepository.findBySeasonIdAndDay(seasonId, i);
            DailyLogoLadderResource dailyLogoLadderResource = new DailyLogoLadderResource();
            List<LogoDayResource> league1 = new ArrayList<>();
            List<LogoDayResource> league2 = new ArrayList<>();
            List<LogoDayResource> league2a = new ArrayList<>();
            List<LogoDayResource> league2b = new ArrayList<>();

            for (LogoDay ld : seasonAndDayLogo) {
                LogoDayResource ldr = new LogoDayResource();

                ldr.setLogoPlayerName(this.logoPlayerRepository.findById(ld.getLogoPlayerId()).orElseThrow().getLogoPlayerName());
                ldr.setPoints(ld.getPoints());
                ldr.setFastest(ld.getIsFastest());

                LogoLadder playerLadder = this.logoLadderRepository.findByLogoPlayerId(ld.getLogoPlayerId(), seasonId);
                if (playerLadder.getLeague() == 1) {
                    league1.add(ldr);
                } else if (!logoSeason.getWithGroups() && playerLadder.getLeague() == 2) {
                    league2.add(ldr);
                } else if (logoSeason.getWithGroups() && playerLadder.getLeague() == 2) {
                    if (playerLadder.getLeagueGroup() == 1) {
                        league2a.add(ldr);
                    } else if (playerLadder.getLeagueGroup() == 2) {
                        league2b.add(ldr);
                    }
                }
            }

            dailyLogoLadderResource.setDay(i);
            dailyLogoLadderResource.setLeague1(league1);
            dailyLogoLadderResource.setLeague2(league2);
            dailyLogoLadderResource.setLeague2a(league2a);
            dailyLogoLadderResource.setLeague2b(league2b);

            dailyLogoLadderResourceList.add(dailyLogoLadderResource);
        }

        dailyLogoLadderResourceList.sort(Comparator.comparing(DailyLogoLadderResource::getDay).reversed());

        return new ResponseEntity<>(dailyLogoLadderResourceList, HttpStatus.OK);
    }

    @CrossOrigin
    @GetMapping("/logo/season")
    public ResponseEntity<LogoSeason> getSeason(@RequestParam String token, @RequestParam int seasonId) {
        Player player = this.playerRepository.findByToken(token).orElseThrow();

        LogoSeason season = this.logoSeasonRepository.findById(seasonId).orElseThrow();

        return new ResponseEntity<>(season, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/logo/day")
    public ResponseEntity<LogoDay> postNewDay(@RequestParam String token, @RequestBody NewDayResource newDayResource) {
        Player player = this.playerRepository.findByToken(token).orElseThrow();

        if (!player.getRole().equals("ADMIN")) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // ENREGISTRER LES MODIFS SAISON
        LogoSeason season = this.logoSeasonRepository.findCurrentSeason();
        List<LogoDay> previousDay = this.logoDayRepository.findBySeasonIdAndDay(season.getId(), season.getCurrentDay());
        int newDay = season.getCurrentDay() + 1;
        season.setCurrentDay(newDay);

        if (newDay == 14) {
            season.setStatus("FINISHED");
        }

        this.logoSeasonRepository.save(season);

        // AJOUTER LES DONNEES DANS LOGO DAY
        // -- LIGUE 1
        for (LogoPlayerData lpd : newDayResource.getLeague1()) {
            LogoPlayer logoPlayer = this.logoPlayerRepository.findByPlayerName(lpd.getPlayerName());
            LogoDay logoDay = new LogoDay();

            logoDay.setDay(newDay);
            logoDay.setLogoPlayerId(logoPlayer.getId());
            logoDay.setPoints(lpd.getPoints());
            logoDay.setIsFastest(lpd.getIsFastest());
            logoDay.setSeasonId(season.getId());

            this.processLadder(logoDay);
            this.logoDayRepository.save(logoDay);
        }
        // -- LIGUE 2
        for (LogoPlayerData lpd : newDayResource.getLeague2()) {
            LogoPlayer logoPlayer = this.logoPlayerRepository.findByPlayerName(lpd.getPlayerName());
            LogoDay logoDay = new LogoDay();

            logoDay.setDay(newDay);
            logoDay.setLogoPlayerId(logoPlayer.getId());
            logoDay.setPoints(lpd.getPoints());
            logoDay.setIsFastest(lpd.getIsFastest());
            logoDay.setSeasonId(season.getId());

            this.processLadder(logoDay);
            this.logoDayRepository.save(logoDay);
        }

        // Process streaks
        List<LogoDay> currentDay = this.logoDayRepository.findBySeasonIdAndDay(season.getId(), newDay);

        this.processStreaks(currentDay, previousDay);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    private void processLadder(LogoDay logoDay) {
        LogoLadder logoLadder = this.logoLadderRepository.findByLogoPlayerId(logoDay.getLogoPlayerId(), logoDay.getSeasonId());

        int newPoints = logoLadder.getTotalPoints() + logoDay.getPoints();

        logoLadder.setDayPoints(logoDay.getPoints());
        logoLadder.setTotalGuessed(logoLadder.getTotalGuessed() + 1);
        logoLadder.setStreak(logoLadder.getStreak() + 1);

        if (logoLadder.getStreak() % 5 == 0) {
            logoLadder.setDayPoints(logoDay.getPoints() + 5);
            newPoints += 5;
        }

        logoLadder.setTotalPoints(newPoints);

        if (logoDay.getIsFastest()) {
            logoLadder.setFastest(logoLadder.getFastest() + 1);
        }

        this.logoLadderRepository.save(logoLadder);
    }

    private void processStreaks(List<LogoDay> current, List<LogoDay> previous) {
        for (LogoDay logoDay : previous) {
            if (current.stream().noneMatch(currentLogoDay -> currentLogoDay.getLogoPlayerId() == logoDay.getLogoPlayerId())) {
                LogoLadder logoLadder = this.logoLadderRepository.findByLogoPlayerId(logoDay.getLogoPlayerId(), logoDay.getSeasonId());
                logoLadder.setStreak(0);
                logoLadder.setDayPoints(0);
                this.logoLadderRepository.save(logoLadder);
            }
        }
    }
}
