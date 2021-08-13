package fr.paniniapiv2.db;

import com.sun.istack.NotNull;
import fr.paniniapiv2.PlayerResource;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "players")
@Getter
@Setter
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String username;

    @NotNull
    private String password;

    private Integer cashCard = 500;

    public Player() {}

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static Player fromResource(PlayerResource resource) {
        Player player = new Player();
        player.setUsername(resource.getUsername());
        player.setPassword(resource.getPassword());

        return player;
    }
}
