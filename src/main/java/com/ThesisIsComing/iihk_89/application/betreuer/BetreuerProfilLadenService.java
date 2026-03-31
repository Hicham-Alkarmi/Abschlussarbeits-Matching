package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import org.springframework.stereotype.Service;

@Service
public class BetreuerProfilLadenService {

    private final BetreuerRepository betreuerRepository;

    public BetreuerProfilLadenService(BetreuerRepository betreuerRepository) {
        this.betreuerRepository = betreuerRepository;
    }

    public BetreuerEditDTO execute(String githubId) {
        Betreuer betreuer = betreuerRepository.findByGithubId(githubId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Betreuer nicht gefunden"));
        return new BetreuerEditDTO(
                betreuer.getName().getVorname(),
                betreuer.getName().getNachname(),
                betreuer.getKontaktinfo().getEmail().getEmail(),
                betreuer.getKontaktinfo().getRaum().getRaumNr(),
                betreuer.getFachgebieteAlsString(),
                betreuer.getLinkUrlsAlsString(),
                betreuer.getLinkTexteAlsString()
        );
    }
}
