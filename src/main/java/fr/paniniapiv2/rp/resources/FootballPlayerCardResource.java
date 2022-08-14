package fr.paniniapiv2.rp.resources;

import fr.paniniapiv2.rp.db.FootballPlayer;
import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;

@Getter
@Setter
public class FootballPlayerCardResource {
    int id;
    String firstname;
    String lastname;
    int number;
    String post;
    String position;
    String mainNationalityCode;
    String mainNationality;
    String secondNationalityCode;
    String secondNationality;
    int age;

    public FootballPlayerCardResource fromFootballPlayer(FootballPlayer footballPlayer) throws ParseException {
        FootballPlayerCardResource footballPlayerCardResource = new FootballPlayerCardResource();

        footballPlayerCardResource.setId(footballPlayer.getId());
        footballPlayerCardResource.setFirstname(footballPlayer.getFirstName());
        footballPlayerCardResource.setLastname(footballPlayer.getLastName());
        footballPlayerCardResource.setNumber(footballPlayer.getNumber());
        footballPlayerCardResource.setPost(footballPlayer.getPost().name());
        footballPlayerCardResource.setPosition(footballPlayer.getPosition().name());
        footballPlayerCardResource.setMainNationality(footballPlayer.getMainNationality().getCountry());
        footballPlayerCardResource.setMainNationalityCode(footballPlayer.getMainNationality().getFlagIndicator());

        if (footballPlayer.getSecondNationality() != null) {
            footballPlayerCardResource.setSecondNationality(footballPlayer.getSecondNationality().getCountry());
            footballPlayerCardResource.setSecondNationalityCode(footballPlayer.getSecondNationality().getFlagIndicator());
        }

        footballPlayerCardResource.setAge(calculateAge(footballPlayer.getBirthday())-2);

        return footballPlayerCardResource;
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
