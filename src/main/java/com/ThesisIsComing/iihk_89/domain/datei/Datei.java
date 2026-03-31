package com.ThesisIsComing.iihk_89.domain.datei;


import java.time.LocalDateTime;
import java.util.Objects;

public class Datei {
    //@Id
    private Long id;
    private final String dateiSystemName;
    private final String uploaderId;
    private final LocalDateTime uploadTime;
    private final String titel;
    private final String beschreibung;
    private final String originalName;

public Datei(Long id,String uploaderId, String titel,
             LocalDateTime uploadTime, String beschreibung,
             String dateiSystemName, String originalName) {
        if (titel == null || titel.isBlank()) throw new IllegalArgumentException("Titel fehlt");
        if (uploaderId == null) throw new IllegalArgumentException("Uploader fehlt");
        if (originalName == null ) throw new IllegalArgumentException("Datei Daten fehlen");
        if (dateiSystemName == null || dateiSystemName.isBlank()) throw new IllegalArgumentException("DateiSystemName fehlt");

        this.id=id;
        this.uploaderId = uploaderId;
        this.titel = titel;
        this.uploadTime = uploadTime;
        this.beschreibung = (beschreibung == null) ? "" : beschreibung;
        this.dateiSystemName = dateiSystemName;
        this.originalName= originalName;
        valiediereEndung();
    }
    public Long getId(){
    return id;
    }
    public String getUploader(){
        return uploaderId;
    }
    public LocalDateTime getUploadTime(){
        return uploadTime;
    }
    public String getTitel(){
        return titel;
    }
    public String getBeschreibung(){
        return beschreibung;
    }
    public String getOriginalName(){
        return originalName;
    }

    public String getDateiSystemName(){
    return dateiSystemName;
    }
    private void valiediereEndung() {
        String nameKlein = originalName.toLowerCase();
        boolean erlaubt = nameKlein.endsWith(".zip") || nameKlein.endsWith(".pdf") || nameKlein.endsWith(".md");
        if (!erlaubt) {
            throw new IllegalArgumentException("Nur .zip, .pdf oder .md Dateien erlaubt!");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Datei datei = (Datei) o;
        return Objects.equals(dateiSystemName, datei.dateiSystemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uploaderId, dateiSystemName);
    }
}
