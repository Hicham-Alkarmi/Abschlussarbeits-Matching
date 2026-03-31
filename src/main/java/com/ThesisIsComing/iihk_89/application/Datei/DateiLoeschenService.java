package com.ThesisIsComing.iihk_89.application.Datei;

import com.ThesisIsComing.iihk_89.domain.datei.Datei;
import org.springframework.stereotype.Service;

@Service
public class DateiLoeschenService {
    private final DateiRepository dateiRepository ;
    private final DateiStorage dateiStorage;

    public DateiLoeschenService(DateiRepository dateiRepository
    , DateiStorage dateiStorage){
       this.dateiRepository=dateiRepository;
       this.dateiStorage = dateiStorage;
    }

    public void execute (String dateiSystemName, String githubid){

         Datei datei = dateiRepository.findByDateiSystemName(dateiSystemName)
                .orElseThrow(() -> new IllegalArgumentException(
                        "Datei nicht gefunden"));
         if(!datei.getUploader().equals(githubid)){
             throw new IllegalStateException("keine Berechtigung diese Datei zu loeschen");
         }
        dateiStorage.loeschen(dateiSystemName);
        dateiRepository.deleteByDateiSystemName(dateiSystemName);
    }



}
