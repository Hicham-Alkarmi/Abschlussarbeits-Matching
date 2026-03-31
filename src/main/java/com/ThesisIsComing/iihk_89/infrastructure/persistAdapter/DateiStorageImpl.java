package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter;

import com.ThesisIsComing.iihk_89.application.Datei.DateiStorage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Repository
public class DateiStorageImpl implements DateiStorage {
    private final Path uploadVerzeichnis;

    public DateiStorageImpl(@Value("${app.upload-verzeichnis}") String pfad) {
        this.uploadVerzeichnis = Paths.get(pfad);
        try {
            Files.createDirectories(uploadVerzeichnis);
        } catch (IOException e) {
            throw new RuntimeException("Upload-Verzeichnis konnte nicht erstellt werden", e);
        }
    }

    @Override
    public void speichern(byte[] inhalt, String dateiSystemName) {
        try {
            Path zielPfad = uploadVerzeichnis.resolve(dateiSystemName);
            Files.write(zielPfad, inhalt);
        } catch (IOException e) {
            throw new RuntimeException("Datei konnte nicht gespeichert werden", e);
        }
    }

    @Override
    public byte[] laden(String dateiSystemName) {
        try {
            Path pfad = uploadVerzeichnis.resolve(dateiSystemName);
            return Files.readAllBytes(pfad);
        } catch (IOException e) {
            throw new RuntimeException("Datei konnte nicht geladen werden", e);
        }
    }

    @Override
    public void loeschen(String dateiSystemName) {
        try {
            Path pfad = uploadVerzeichnis.resolve(dateiSystemName);
            Files.deleteIfExists(pfad);
        } catch (IOException e) {
            throw new RuntimeException("Datei konnte nicht gelöscht werden", e);
        }
    }
}
