package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WhoAmILadderResource {
    private int id;
    private int seasonId;
    private int streak;
    private int totalGuessed;
    private int totalPoints;
    private int dayPoints;
    private String playerName;
    private String playerAvatar;
    private String titleColor;
    private String titleLabel;
}
