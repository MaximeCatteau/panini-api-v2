package fr.paniniapiv2.rp.resources;

import fr.paniniapiv2.rp.db.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
public class FootballPlayerProfileResource {
    private int id;
    private String firstname;
    private String lastname;
    private int number;
    private String mainNationality;
    private String mainNationalityFlag;
    private String secondNationality;
    private String secondNationalityFlag;
    private String birthDate;
    private int age;
    private String post;
    private String position;
    private String mainFoot;
    private CharacterStatistics characterStatistics;
    private MentalAttributes mentalAttributes;
    private PhysicalAttributes physicalAttributes;
    private TechnicalAttributes technicalAttributes;
    private int level;
    private int experience;
    private int pointsToSet;
    private Long ownerId;

    public FootballPlayerProfileResource fromFootballPlayer(FootballPlayer footballPlayer) {
        FootballPlayerProfileResource footballPlayerProfileResource = new FootballPlayerProfileResource();

        footballPlayerProfileResource.setId(footballPlayer.getId());
        footballPlayerProfileResource.setFirstname(footballPlayer.getFirstName());
        footballPlayerProfileResource.setLastname(footballPlayer.getLastName());
        footballPlayerProfileResource.setNumber(footballPlayer.getNumber());
        footballPlayerProfileResource.setMainNationality(footballPlayer.getMainNationality().getCountry());
        footballPlayerProfileResource.setMainNationalityFlag(footballPlayer.getMainNationality().getFlagIndicator());

        if (footballPlayer.getSecondNationality() != null) {
            footballPlayerProfileResource.setSecondNationality(footballPlayer.getSecondNationality().getCountry());
            footballPlayerProfileResource.setSecondNationalityFlag(footballPlayer.getSecondNationality().getFlagIndicator());
        }

        footballPlayerProfileResource.setBirthDate(footballPlayer.getBirthday());
        footballPlayerProfileResource.setAge(calculateAge(footballPlayer.getBirthday())-2);
        footballPlayerProfileResource.setPost(footballPlayer.getPost().name());
        footballPlayerProfileResource.setPosition(footballPlayer.getPosition().name());
        footballPlayerProfileResource.setMainFoot(footballPlayer.getFootPreference().name());
        footballPlayerProfileResource.setCharacterStatistics(footballPlayer.getCharacterStatistics());
        footballPlayerProfileResource.setMentalAttributes(footballPlayer.getMentalAttributes());
        footballPlayerProfileResource.setPhysicalAttributes(footballPlayer.getPhysicalAttributes());
        footballPlayerProfileResource.setTechnicalAttributes(footballPlayer.getTechnicalAttributes());
        footballPlayerProfileResource.setLevel(footballPlayer.getLevel());
        footballPlayerProfileResource.setExperience(footballPlayer.getExperience());
        footballPlayerProfileResource.setOwnerId(footballPlayer.getOwner().getId());
        footballPlayerProfileResource.setPointsToSet(footballPlayer.getPointsToSet());

        return footballPlayerProfileResource;
    }

    public static int calculateAge(String birthDate) {
        LocalDate currentDate = LocalDate.now();
        LocalDate birthday = LocalDate.parse(birthDate);
        if ((birthday != null)) {
            return Period.between(birthday, currentDate).getYears();
        } else {
            return 0;
        }
    }
}
