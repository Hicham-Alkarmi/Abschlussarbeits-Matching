package com.ThesisIsComing.iihk_89.application.thema;

import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class BetreuerThemenAnzeigenService {
    private final ThemaRepository themaRepository;
    private final BetreuerRepository betreuerRepository;

    public BetreuerThemenAnzeigenService(ThemaRepository themaRepository,
                                         BetreuerRepository betreuerRepository){
        this.themaRepository=themaRepository;
        this.betreuerRepository=betreuerRepository;

    }

    public List<Thema> execute(String betreuerId){
        if (!betreuerRepository.existsByGithubId(betreuerId)) {
            throw new IllegalArgumentException("Betreuer mit ID " + betreuerId + " wurde nicht gefunden.");
        }
         return themaRepository.findAllByBetreuerId(betreuerId);
    }
}

