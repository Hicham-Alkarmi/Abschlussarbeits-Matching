package com.ThesisIsComing.iihk_89.application.Datei;


import org.springframework.stereotype.Service;

@Service
public class DateiDownloadService {
    private final DateiRepository dateiRepository;
    private final DateiStorage dateiStorage;

    public DateiDownloadService(DateiRepository dateiRepository,
                                DateiStorage dateiStorage){
        this.dateiRepository=dateiRepository;
        this.dateiStorage=dateiStorage;
    }

    public byte[] execute(String dateiSystemName){

        if(!dateiRepository.existsByDateiSystemName(dateiSystemName)){
            throw new IllegalArgumentException("Datei nicht gefunden: " + dateiSystemName);
        }
        byte[] dateiInhalt = dateiStorage.laden(dateiSystemName);
        return  dateiInhalt ;
    }
}
