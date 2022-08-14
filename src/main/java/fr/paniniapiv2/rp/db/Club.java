package fr.paniniapiv2.rp.db;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "club")
@Getter
@Setter
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class Club {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public int id;

    public String name;

    public float budget;

    public String logo;

    public String primaryColor;

    public String secondaryColor;

    @OneToOne
    private Infrastructures infrastructures;
}
