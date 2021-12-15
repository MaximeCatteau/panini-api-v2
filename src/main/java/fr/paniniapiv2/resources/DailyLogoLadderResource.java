package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DailyLogoLadderResource {
    private String day;
    private List<LogoDayResource> league1;
    private List<LogoDayResource> league2;
}
