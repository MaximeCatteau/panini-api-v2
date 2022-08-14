package fr.paniniapiv2.rp.services;

import fr.paniniapiv2.rp.db.Competition;
import fr.paniniapiv2.rp.repositories.CompetitionRepository;
import fr.paniniapiv2.rp.resources.CreateCompetitionResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompetitionService {
    @Autowired
    CompetitionRepository competitionRepository;

    public Competition createNewCompetition(CreateCompetitionResource createCompetitionResource) {
        Competition competition = new Competition();

        competition.setName(createCompetitionResource.getName());
        competition.setYear(createCompetitionResource.getYear());
        competition.setWinner(null);

        return this.competitionRepository.save(competition);
    }

    public List<Competition> getAllCompetition() {
        return this.competitionRepository.findAll();
    }
}
