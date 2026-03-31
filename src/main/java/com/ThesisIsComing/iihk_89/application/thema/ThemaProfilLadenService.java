package com.ThesisIsComing.iihk_89.application.thema;

import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.springframework.stereotype.Service;

@Service
public class ThemaProfilLadenService {
    private final ThemaRepository themaRepository;

    public ThemaProfilLadenService(ThemaRepository themaRepository){
        this.themaRepository=themaRepository;
    }
    public ThemaDTO execute(Long themaId){
        Thema thema =themaRepository.findById(themaId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Thema nicht gefunden: " + themaId));

        return new ThemaDTO(
                thema.getTitel(),
                thema.getBeschreibung(),
                thema.getBetreuerId(),
                thema.getFachgebieteAlsString(),
                thema.getVeranstaltungenAlsString(),
                thema.getLinkUrlsAlsString(),
                thema.getLinkTexteAlsString());
    }
}
