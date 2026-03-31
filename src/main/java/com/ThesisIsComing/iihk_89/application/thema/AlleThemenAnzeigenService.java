package com.ThesisIsComing.iihk_89.application.thema;

import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AlleThemenAnzeigenService {
    private final ThemaRepository themaRepository;

    public AlleThemenAnzeigenService(ThemaRepository themaRepository) {
        this.themaRepository=themaRepository;
    }

    public List<Thema> execute(){
        return themaRepository.findAll();
    }
}
