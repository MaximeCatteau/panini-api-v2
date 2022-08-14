package fr.paniniapiv2.rp.resources;

import fr.paniniapiv2.rp.db.Club;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClubResource {
    private int id;

    private String clubName;

    public float budget;

    public String logo;

    public String primaryColor;

    public String secondaryColor;

    public static ClubResource fromClub(Club club) {
        ClubResource clubResource = new ClubResource();

        clubResource.setId(club.getId());
        clubResource.setClubName(club.getName());
        clubResource.setBudget(club.getBudget());
        clubResource.setLogo(club.getLogo());
        clubResource.setPrimaryColor(club.getPrimaryColor());
        clubResource.setSecondaryColor(club.getSecondaryColor());

        return clubResource;
    }
}
