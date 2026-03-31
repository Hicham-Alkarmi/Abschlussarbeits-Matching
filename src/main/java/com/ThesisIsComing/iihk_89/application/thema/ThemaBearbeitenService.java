package com.ThesisIsComing.iihk_89.application.thema;

import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.springframework.stereotype.Service;

@Service
public class ThemaBearbeitenService {
    private final ThemaRepository themaRepository;

    public ThemaBearbeitenService(ThemaRepository themaRepository){
        this.themaRepository=themaRepository;
    }
    public void execute(Long themaId,ThemaDTO themaDTO){

       Thema thema = themaRepository.findById(themaId)
               .orElseThrow(() -> new IllegalArgumentException(
                       "Thema nicht gefunden: " + themaId));

       thema.updateTitel(themaDTO.titel());

       thema.updateBeschreibung(themaDTO.beschreibung());

       thema.updateFachgebiete(themaDTO.fachgebiete());

       thema.updateVeranstaltungen(themaDTO.empfohleneVeranstaltungen());

       thema.updateLinks(themaDTO.linkUri(),themaDTO.linkText());
       themaRepository.save(thema);
    }
}
