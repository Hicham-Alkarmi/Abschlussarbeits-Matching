package com.ThesisIsComing.iihk_89.application.thema;

import org.springframework.stereotype.Service;

@Service
public class ThemenloeschenService {
    private final ThemaRepository themaRepository;

    public ThemenloeschenService(ThemaRepository themaRepository){
        this.themaRepository=themaRepository;
    }

    public void  execute(Long themaId){
        if (!themaRepository.findById(themaId).isPresent())
            throw new IllegalArgumentException("Thema nicht gefunden");
        themaRepository.delete(themaId);
    }
}
