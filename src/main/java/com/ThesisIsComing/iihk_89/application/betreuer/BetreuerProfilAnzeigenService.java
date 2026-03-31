package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import org.springframework.stereotype.Service;

@Service
public class BetreuerProfilAnzeigenService {

    private final BetreuerRepository betreuerRepository;

    public BetreuerProfilAnzeigenService(BetreuerRepository betreuerRepository){
        this.betreuerRepository=betreuerRepository;
    }
    public Betreuer execute(String githubId){
        Betreuer betreuer = betreuerRepository.findByGithubId(githubId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Betreuer nicht gefunden: " + githubId));
        return betreuer;
    }

}
