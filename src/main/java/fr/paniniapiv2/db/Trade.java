package fr.paniniapiv2.db;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "trades")
@Getter
@Setter
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Long transmitterId;

    private Long recipientId;

    private Integer transmitterCardId;

    private Integer recipientCardId;

    private String tradeStatus;
}
