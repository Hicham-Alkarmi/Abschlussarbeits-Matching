package com.ThesisIsComing.iihk_89.application.Datei.UploadServices;

import com.ThesisIsComing.iihk_89.application.Datei.DateiRepository;
import com.ThesisIsComing.iihk_89.application.Datei.DateiStorage;
import com.ThesisIsComing.iihk_89.application.Datei.DateiUploadDTO;
import com.ThesisIsComing.iihk_89.application.thema.ThemaRepository;
import com.ThesisIsComing.iihk_89.domain.datei.Datei;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
@Service
@Transactional
public class DateiUploadZuThemaService {
    private final DateiStorage dateiStorage;
    private final DateiRepository dateiRepository;
    private final ThemaRepository themaRepository;

    public DateiUploadZuThemaService(DateiStorage dateiStorage,
                                     DateiRepository dateiRepository,
                                     ThemaRepository themaRepository){
        this.dateiRepository=dateiRepository;
        this.dateiStorage=dateiStorage;
        this.themaRepository=themaRepository;
    }

    public void execute(DateiUploadDTO dateiDto, Long themaId){
        if(dateiDto.groesse() > 10L*1024*1024){
            throw  new IllegalArgumentException("Datei zu gross! Max 10MB");
        }

        Thema thema = themaRepository.findById(themaId)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Thema nicht gefunden: " + themaId));

        String endung = dateiDto.originalName().substring(dateiDto.originalName().lastIndexOf("."));
        String dateiSystemName = UUID.randomUUID() + endung;



        Datei datei = new Datei(
                null,
                dateiDto.betreuerId(),
                dateiDto.titel(),
                LocalDateTime.now(),
                dateiDto.beschreibung(),
                dateiSystemName,
                dateiDto.originalName());

        byte[] inhalt = dateiDto.inhalt();

        dateiStorage.speichern(inhalt,dateiSystemName);
        dateiRepository.save(datei);

        thema.dateiHinzufuegen(dateiSystemName);
        themaRepository.save(thema);
    }
}
