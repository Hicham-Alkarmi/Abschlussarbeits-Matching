package com.ThesisIsComing.iihk_89.application.Datei;

import com.ThesisIsComing.iihk_89.domain.datei.Datei;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class DateiLadenService {

    private final DateiRepository dateiRepository;

    public DateiLadenService(DateiRepository dateiRepository) {
        this.dateiRepository = dateiRepository;
    }

    public List<Datei> ladeAlleBySystemNamen(
            Set<String> dateiSystemNamen) {
        System.out.println("Lade Dateien für: " + dateiSystemNamen);
        return dateiSystemNamen.stream()
                .map(dateiRepository::findByDateiSystemName)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .filter(d -> d.getDateiSystemName() != null)
                .toList();
    }
}