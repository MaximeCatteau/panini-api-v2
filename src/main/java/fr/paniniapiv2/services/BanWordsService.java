package fr.paniniapiv2.services;

import fr.paniniapiv2.db.BanWords;
import fr.paniniapiv2.repositories.BanWordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BanWordsService {
    @Autowired
    BanWordsRepository banWordsRepository;

    public BanWordsService(BanWordsRepository banWordsRepository) {
        this.banWordsRepository = banWordsRepository;
    }

    public BanWords addBanWord(String banWord, String addedBy) {
        if (wordAlreadyExists(banWord)) {
            return null;
        } else {
            BanWords bw = new BanWords();

            bw.setWord(banWord);
            bw.setAddedBy(addedBy);

            this.banWordsRepository.save(bw);
            return bw;
        }
    }

    public BanWords removeBanWord(String banWord) {
        if (!wordAlreadyExists(banWord)) {
            return null;
        } else {
            BanWords bw = this.banWordsRepository.findByWord(banWord);

            this.banWordsRepository.delete(bw);
            return bw;
        }
    }

    public List<BanWords> getAllBanWords() {
        return this.banWordsRepository.findAll();
    }

    public boolean wordAlreadyExists(String word) {
        return this.banWordsRepository.existsByWord(word);
    }
}
