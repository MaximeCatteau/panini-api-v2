package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "whoAmIPlayer")
@Getter
@Setter
public class WhoAmIPlayer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long playerIdAssociated;

    private String discordId;
}
