package com.ThesisIsComing.iihk_89.application.Datei.UploadServices;

import com.ThesisIsComing.iihk_89.application.Datei.DateiRepository;
import com.ThesisIsComing.iihk_89.application.Datei.DateiStorage;
import com.ThesisIsComing.iihk_89.application.Datei.DateiUploadDTO;
import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import com.ThesisIsComing.iihk_89.domain.datei.Datei;
import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@Transactional
public class DateiUploadZuBetreuerService {
    private final DateiRepository dateiRepository;
    private final DateiStorage dateiStorage;
    private final BetreuerRepository betreuerRepository;

    public DateiUploadZuBetreuerService(DateiRepository dateiRepository,
                                        DateiStorage dateiStorage,
                                        BetreuerRepository betreuerRepository){
        this.dateiRepository=dateiRepository;
        this.dateiStorage =dateiStorage;
        this.betreuerRepository=betreuerRepository;
    }

    public void execute(DateiUploadDTO dto){
        if(dto.groesse() > 10L*1024*1024){
            throw  new IllegalArgumentException("Datei zu gross! Max 10MB");
        }

        Betreuer betreuer = betreuerRepository.findByGithubId(dto.betreuerId())
                .orElseThrow(() -> new IllegalArgumentException(
                        "Betreuer nicht gefunden: " + dto.betreuerId()));

        String orginalName = dto.originalName();
        String endung = orginalName.substring(orginalName.lastIndexOf("."));
        String dateiSystemName = UUID.randomUUID() + endung;

        String betreuerId = dto.betreuerId();
        LocalDateTime uploadTime = LocalDateTime.now();
        String titel = dto.titel();
        String beschreibung = dto.beschreibung();

        Datei datei = new Datei(null,betreuerId,titel,uploadTime,beschreibung,dateiSystemName,orginalName);

        byte[] inhalt = dto.inhalt();

        dateiStorage.speichern(inhalt,dateiSystemName);
        dateiRepository.save(datei);

        betreuer.dateiHinzufuegen(dateiSystemName);
        betreuerRepository.save(betreuer);
    }
}
