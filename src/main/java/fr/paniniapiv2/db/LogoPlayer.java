package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "logoPlayer")
@Getter
@Setter
public class LogoPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long playerIdAssociated;

    private String logoPlayerName;

    private String discordId;
}
