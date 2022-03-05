package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class DailyLogoLadderResource {
    private int day;
    private List<LogoDayResource> league1;
    private List<LogoDayResource> league2;
    private List<LogoDayResource> league2a;
    private List<LogoDayResource> league2b;
}
