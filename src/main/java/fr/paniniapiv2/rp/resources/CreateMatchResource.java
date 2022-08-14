package fr.paniniapiv2.rp.resources;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateMatchResource {
    String home;

    String away;

    String date;
}
