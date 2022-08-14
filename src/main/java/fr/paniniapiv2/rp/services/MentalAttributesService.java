package fr.paniniapiv2.rp.services;

import fr.paniniapiv2.rp.db.FootballPlayer;
import fr.paniniapiv2.rp.db.MentalAttributes;
import fr.paniniapiv2.rp.repositories.FootballPlayerRepository;
import fr.paniniapiv2.rp.repositories.MentalAttributesRepository;
import fr.paniniapiv2.rp.resources.UpdateMentalAttributesResource;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class MentalAttributesService {
    @Autowired
    MentalAttributesRepository mentalAttributesRepository;

    @Autowired
    FootballPlayerRepository footballPlayerRepository;

    /**
     * DISCORD
     * @param resource
     * @return
     */
    DiscordApi api = new DiscordApiBuilder()
            .setToken("OTEwMDg2MTQyNTgwMzY3Mzgw.YZNtxA.qZBFOb7Ro2yct4Ddv_AQAEYTs50")
            .login().join();

    public void updateMentalAttributes(UpdateMentalAttributesResource updateMentalAttributesResource) {
        FootballPlayer footballPlayer = this.footballPlayerRepository.getById(updateMentalAttributesResource.getFootballPlayerId());

        MentalAttributes mentalAttributesToUpdate = footballPlayer.getMentalAttributes();

        mentalAttributesToUpdate.setAggressiveness(updateMentalAttributesResource.getAggressiveness());
        mentalAttributesToUpdate.setBallCalls(updateMentalAttributesResource.getBallCalls());
        mentalAttributesToUpdate.setBravery(updateMentalAttributesResource.getBravery());
        mentalAttributesToUpdate.setDetermination(updateMentalAttributesResource.getDetermination());
        mentalAttributesToUpdate.setCollectiveGame(updateMentalAttributesResource.getCollectiveGame());
        mentalAttributesToUpdate.setPlacement(updateMentalAttributesResource.getPlacement());
        mentalAttributesToUpdate.setGameView(updateMentalAttributesResource.getGameView());
        mentalAttributesToUpdate.setAnticipation(updateMentalAttributesResource.getAnticipation());
        mentalAttributesToUpdate.setConcentration(updateMentalAttributesResource.getConcentration());
        mentalAttributesToUpdate.setDecisionMaking(updateMentalAttributesResource.getDecisionMaking());
        mentalAttributesToUpdate.setInspiration(updateMentalAttributesResource.getInspiration());
        mentalAttributesToUpdate.setLeadership(updateMentalAttributesResource.getLeadership());
        mentalAttributesToUpdate.setColdBlood(updateMentalAttributesResource.getColdBlood());
        mentalAttributesToUpdate.setGameVolume(updateMentalAttributesResource.getGameVolume());


        CompletableFuture<User> user = api.getUserById("185790407156826113");

        try {
            PrivateChannel pc = user.get().openPrivateChannel().get();
            pc.sendMessage(footballPlayer.getFirstName() + " " + footballPlayer.getLastName() +
                    " a augmenté sa compétence mentale [" + updateMentalAttributesResource.getAttributeUpdated() + "]");
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.mentalAttributesRepository.save(mentalAttributesToUpdate);

        footballPlayer.setPointsToSet(footballPlayer.getPointsToSet()-1);

        this.footballPlayerRepository.save(footballPlayer);
    }
}
