package com.ThesisIsComing.iihk_89.application.matching;

import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import com.ThesisIsComing.iihk_89.application.thema.ThemaRepository;
import com.ThesisIsComing.iihk_89.domainService.MatchErgebnis;
import com.ThesisIsComing.iihk_89.domainService.MatchingBerechner;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MatchingService {
    private final ThemaRepository themaRepository;
    private final BetreuerRepository betreuerRepository;

    public MatchingService(ThemaRepository themaRepository,
                           BetreuerRepository betreuerRepository) {
        this.themaRepository = themaRepository;
        this.betreuerRepository = betreuerRepository;
    }

    public List<MatchErgebnis> execute(MatchingAnfrageDTO dto) {
        return new MatchingBerechner().berechne(
                dto.fachgebieteInput(),
                dto.veranstaltungenInput(),
                themaRepository.findAll(),
                betreuerRepository.findAll()
        );
    }
    public Set<String> alleFachgebiete() {
        Set<String> fachgebiete = new HashSet<>();
        betreuerRepository.findAll().forEach(b ->
                b.getFachgebiete().forEach(f ->
                        fachgebiete.add(f.getFachgebiet())));
        themaRepository.findAll().forEach(t ->
                t.getFachgebiete().forEach(f ->
                        fachgebiete.add(f.getFachgebiet())));
        return fachgebiete;
    }
}
