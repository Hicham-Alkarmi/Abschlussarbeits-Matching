package com.ThesisIsComing.iihk_89.application.thema;

import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.springframework.stereotype.Service;

@Service
public class ThemaAnzeigenService {
    private final ThemaRepository themaRepository;

    public ThemaAnzeigenService(ThemaRepository themaRepository){
        this.themaRepository=themaRepository;
    }

    public Thema execute(Long themaId){
       return themaRepository.findById(themaId)
               .orElseThrow(()-> new IllegalArgumentException(
                "Thema nicht gefunden: " + themaId));
    }
}
