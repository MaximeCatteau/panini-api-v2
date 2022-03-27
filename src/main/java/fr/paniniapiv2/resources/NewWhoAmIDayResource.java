package fr.paniniapiv2.resources;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewWhoAmIDayResource {
    List<WhoAmIPlayerDataResource> whoAmIPlayerResourceList;
}
