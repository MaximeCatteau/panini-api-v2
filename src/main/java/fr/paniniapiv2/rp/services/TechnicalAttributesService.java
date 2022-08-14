package fr.paniniapiv2.rp.services;

import fr.paniniapiv2.rp.db.FieldPlayerAttributes;
import fr.paniniapiv2.rp.db.FootballPlayer;
import fr.paniniapiv2.rp.db.GoalkeeperAttributes;
import fr.paniniapiv2.rp.db.PhysicalAttributes;
import fr.paniniapiv2.rp.repositories.FieldPlayerAttributesRepository;
import fr.paniniapiv2.rp.repositories.FootballPlayerRepository;
import fr.paniniapiv2.rp.repositories.GoalKeeperAttributesRepository;
import fr.paniniapiv2.rp.resources.UpdatePhysicalAttributesResource;
import fr.paniniapiv2.rp.resources.UpdateTechnicalFieldPlayerAttributesResource;
import fr.paniniapiv2.rp.resources.UpdateTechnicalGoalKeeperAttributesResource;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class TechnicalAttributesService {
    @Autowired
    FieldPlayerAttributesRepository fieldPlayerAttributesRepository;

    @Autowired
    GoalKeeperAttributesRepository goalKeeperAttributesRepository;

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

    public void updateFieldPlayerTechnicalAttributes(UpdateTechnicalFieldPlayerAttributesResource updateTechnicalFieldPlayerAttributesResource) {
        FootballPlayer footballPlayer = this.footballPlayerRepository.getById(updateTechnicalFieldPlayerAttributesResource.getFootballPlayerId());

        FieldPlayerAttributes fieldPlayerAttributesToUpdate = (FieldPlayerAttributes) footballPlayer.getTechnicalAttributes();

        fieldPlayerAttributesToUpdate.setCenters(updateTechnicalFieldPlayerAttributesResource.getCenters());
        fieldPlayerAttributesToUpdate.setCorners(updateTechnicalFieldPlayerAttributesResource.getCorners());
        fieldPlayerAttributesToUpdate.setDribbles(updateTechnicalFieldPlayerAttributesResource.getDribbles());
        fieldPlayerAttributesToUpdate.setHeaders(updateTechnicalFieldPlayerAttributesResource.getHeaders());
        fieldPlayerAttributesToUpdate.setPasses(updateTechnicalFieldPlayerAttributesResource.getPasses());
        fieldPlayerAttributesToUpdate.setTackles(updateTechnicalFieldPlayerAttributesResource.getTackles());
        fieldPlayerAttributesToUpdate.setLongShots(updateTechnicalFieldPlayerAttributesResource.getLongShots());
        fieldPlayerAttributesToUpdate.setBallControl(updateTechnicalFieldPlayerAttributesResource.getBallControl());
        fieldPlayerAttributesToUpdate.setFreeKicks(updateTechnicalFieldPlayerAttributesResource.getFreeKicks());
        fieldPlayerAttributesToUpdate.setFinition(updateTechnicalFieldPlayerAttributesResource.getFinition());
        fieldPlayerAttributesToUpdate.setMarking(updateTechnicalFieldPlayerAttributesResource.getMarking());
        fieldPlayerAttributesToUpdate.setPenalty(updateTechnicalFieldPlayerAttributesResource.getPenalty());
        fieldPlayerAttributesToUpdate.setTechnique(updateTechnicalFieldPlayerAttributesResource.getTechnique());
        fieldPlayerAttributesToUpdate.setLongThrows(updateTechnicalFieldPlayerAttributesResource.getLongThrows());

        CompletableFuture<User> user = api.getUserById("185790407156826113");

        try {
            PrivateChannel pc = user.get().openPrivateChannel().get();
            pc.sendMessage(footballPlayer.getFirstName() + " " + footballPlayer.getLastName() +
                    " a augmenté sa compétence technique [" + updateTechnicalFieldPlayerAttributesResource.getAttributeUpdated() + "]");
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.fieldPlayerAttributesRepository.save(fieldPlayerAttributesToUpdate);

        footballPlayer.setPointsToSet(footballPlayer.getPointsToSet()-1);

        this.footballPlayerRepository.save(footballPlayer);
    }

    public void updateGoalkeeperTechnicalAttributes(UpdateTechnicalGoalKeeperAttributesResource updateTechnicalGoalKeeperAttributesResource) {
        FootballPlayer footballPlayer = this.footballPlayerRepository.getById(updateTechnicalGoalKeeperAttributesResource.getFootballPlayerId());

        GoalkeeperAttributes goalkeeperAttributes = (GoalkeeperAttributes) footballPlayer.getTechnicalAttributes();

        goalkeeperAttributes.setCommunication(updateTechnicalGoalKeeperAttributesResource.getCommunication());
        goalkeeperAttributes.setBallControl(updateTechnicalGoalKeeperAttributesResource.getBallControl());
        goalkeeperAttributes.setFistClearances(updateTechnicalGoalKeeperAttributesResource.getFistClearances());
        goalkeeperAttributes.setClearances(updateTechnicalGoalKeeperAttributesResource.getClearances());
        goalkeeperAttributes.setExtension(updateTechnicalGoalKeeperAttributesResource.getExtension());
        goalkeeperAttributes.setExcentricity(updateTechnicalGoalKeeperAttributesResource.getExcentricity());
        goalkeeperAttributes.setPasses(updateTechnicalGoalKeeperAttributesResource.getPasses());
        goalkeeperAttributes.setBallCatches(updateTechnicalGoalKeeperAttributesResource.getBallCatches());
        goalkeeperAttributes.setReflexes(updateTechnicalGoalKeeperAttributesResource.getReflexes());
        goalkeeperAttributes.setHandThrows(updateTechnicalGoalKeeperAttributesResource.getHandThrows());
        goalkeeperAttributes.setInboxExits(updateTechnicalGoalKeeperAttributesResource.getInboxExits());
        goalkeeperAttributes.setInFeetExits(updateTechnicalGoalKeeperAttributesResource.getInFeetExits());
        goalkeeperAttributes.setOneVersusOne(updateTechnicalGoalKeeperAttributesResource.getOneVersusOne());

        CompletableFuture<User> user = api.getUserById("185790407156826113");

        try {
            PrivateChannel pc = user.get().openPrivateChannel().get();
            pc.sendMessage(footballPlayer.getFirstName() + " " + footballPlayer.getLastName() +
                    " a augmenté sa compétence technique [" + updateTechnicalGoalKeeperAttributesResource.getAttributeUpdated() + "]");
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.goalKeeperAttributesRepository.save(goalkeeperAttributes);

        footballPlayer.setPointsToSet(footballPlayer.getPointsToSet()-1);

        this.footballPlayerRepository.save(footballPlayer);
    }
}
