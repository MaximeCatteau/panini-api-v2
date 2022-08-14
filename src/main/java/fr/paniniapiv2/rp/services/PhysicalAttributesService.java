package fr.paniniapiv2.rp.services;

import fr.paniniapiv2.rp.db.FootballPlayer;
import fr.paniniapiv2.rp.db.PhysicalAttributes;
import fr.paniniapiv2.rp.repositories.FootballPlayerRepository;
import fr.paniniapiv2.rp.repositories.PhysicalAttributesRepository;
import fr.paniniapiv2.rp.resources.UpdatePhysicalAttributesResource;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.channel.PrivateChannel;
import org.javacord.api.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class PhysicalAttributesService {
    @Autowired
    PhysicalAttributesRepository physicalAttributesRepository;

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

    public void updatePhysicalAttributes(UpdatePhysicalAttributesResource updatePhysicalAttributesResource) {
        FootballPlayer footballPlayer = this.footballPlayerRepository.getById(updatePhysicalAttributesResource.getFootballPlayerId());

        PhysicalAttributes physicalAttributesToUpdate = footballPlayer.getPhysicalAttributes();

        physicalAttributesToUpdate.setAcceleration(updatePhysicalAttributesResource.getAcceleration());
        physicalAttributesToUpdate.setAgility(updatePhysicalAttributesResource.getAgility());
        physicalAttributesToUpdate.setEndurance(updatePhysicalAttributesResource.getEndurance());
        physicalAttributesToUpdate.setEquilibrium(updatePhysicalAttributesResource.getEquilibrium());
        physicalAttributesToUpdate.setNaturalPhysicAbilities(updatePhysicalAttributesResource.getNaturalPhysicAbilities());
        physicalAttributesToUpdate.setPower(updatePhysicalAttributesResource.getPower());
        physicalAttributesToUpdate.setSpeed(updatePhysicalAttributesResource.getSpeed());
        physicalAttributesToUpdate.setVerticalExtension(updatePhysicalAttributesResource.getVerticalExtension());

        CompletableFuture<User> user = api.getUserById("185790407156826113");

        try {
            PrivateChannel pc = user.get().openPrivateChannel().get();
            pc.sendMessage(footballPlayer.getFirstName() + " " + footballPlayer.getLastName() +
                    " a augmenté sa compétence physique [" + updatePhysicalAttributesResource.getAttributeUpdated() + "]");
        } catch(Exception e) {
            e.printStackTrace();
        }

        this.physicalAttributesRepository.save(physicalAttributesToUpdate);

        footballPlayer.setPointsToSet(footballPlayer.getPointsToSet()-1);

        this.footballPlayerRepository.save(footballPlayer);
    }
}
