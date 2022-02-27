package fr.paniniapiv2.services;

import fr.paniniapiv2.repositories.CodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeService {
    @Autowired
    CodeRepository codeRepository;

    public CodeService (CodeRepository codeRepository) {
        this.codeRepository = codeRepository;
    }

    public List<String> getPlayerCodesByDiscordId(String discordId) {
        return this.codeRepository.getCodesForPlayerByDiscordId(discordId);
    }
}
