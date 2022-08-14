package fr.paniniapiv2.rp.resources;

import fr.paniniapiv2.rp.db.MatchNote;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchNoteResource {
    private int footballPlayerId;

    private float matchNote;

    public static MatchNoteResource fromMatchNote(MatchNote matchNote) {
        MatchNoteResource matchNoteResource = new MatchNoteResource();

        matchNoteResource.setFootballPlayerId(matchNote.getFootballPlayer().getId());
        matchNoteResource.setMatchNote(matchNote.getNote());

        return matchNoteResource;
    }
}
