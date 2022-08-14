package fr.paniniapiv2.rp.resources;

import fr.paniniapiv2.rp.db.FootballPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FootballPlayerAdminResource {
    private int id;

    private String firstname;

    private String lastname;

    private String owner;

    private String ownerDiscordId;

    private int level;

    private int xp;

    public FootballPlayerAdminResource fromFootballPlayer(FootballPlayer footballPlayer) {
        FootballPlayerAdminResource footballPlayerAdminResource = new FootballPlayerAdminResource();

        footballPlayerAdminResource.setId(footballPlayer.getId());
        footballPlayerAdminResource.setFirstname(footballPlayer.getFirstName());
        footballPlayerAdminResource.setLastname(footballPlayer.getLastName());
        footballPlayerAdminResource.setOwner(footballPlayer.getOwner().getUsername());
        footballPlayerAdminResource.setOwnerDiscordId(footballPlayer.getOwner().getDiscordId());
        footballPlayerAdminResource.setLevel(footballPlayer.getLevel());
        footballPlayerAdminResource.setXp(footballPlayer.getExperience());

        return footballPlayerAdminResource;
    }
}
