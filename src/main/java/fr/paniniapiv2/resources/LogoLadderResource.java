package fr.paniniapiv2.resources;

import fr.paniniapiv2.db.PlayerTitle;
import fr.paniniapiv2.db.Title;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LogoLadderResource {
    private String logoPlayerName;
    private int totalPoints;
    private int dayPoints;
    private int logoGuessed;
    private int streak;
    private int fastest;
    private String playerTitle;
    private String color;
    private String profileImageUrl;
}
