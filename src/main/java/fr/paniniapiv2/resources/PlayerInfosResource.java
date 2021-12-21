package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlayerInfosResource {
    private Long id;
    private String name;
    private boolean isLogoGuesser;
    private List<String> titles;
    private String selectedTitle;
    private String selectedTitleColor;
}
