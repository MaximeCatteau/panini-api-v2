package fr.paniniapiv2.rp.resources;

import fr.paniniapiv2.rp.db.FootballPlayer;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FootballPlayerResource {
    private int id;

    private String firstname;

    private String lastname;

    public static FootballPlayerResource fromFootballPlayer(FootballPlayer footballPlayer) {
        FootballPlayerResource footballPlayerResource = new FootballPlayerResource();

        footballPlayerResource.setId(footballPlayer.getId());
        footballPlayerResource.setFirstname(footballPlayer.getFirstName());
        footballPlayerResource.setLastname(footballPlayer.getLastName());

        return footballPlayerResource;
    }
}
