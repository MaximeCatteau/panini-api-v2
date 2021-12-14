package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class NewDayResource {
    List<LogoPlayerData> league1 = new ArrayList<>();
    List<LogoPlayerData> league2 = new ArrayList<>();
}
