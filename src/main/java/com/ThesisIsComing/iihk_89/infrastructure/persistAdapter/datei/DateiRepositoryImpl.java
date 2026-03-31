package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.datei;

import com.ThesisIsComing.iihk_89.application.Datei.DateiRepository;
import com.ThesisIsComing.iihk_89.domain.datei.Datei;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class DateiRepositoryImpl implements DateiRepository {

    private final DateiDAO dateiDAO;

    public DateiRepositoryImpl(DateiDAO dateiDAO) {
        this.dateiDAO = dateiDAO;
    }

    private DateiRecord toRecord(Datei d) {
        return new DateiRecord(
                d.getId(),
                d.getDateiSystemName(),
                d.getUploader(),
                d.getUploadTime(),
                d.getTitel(),
                d.getBeschreibung(),
                d.getOriginalName()
        );
    }

    private Datei toDomain(DateiRecord r) {
        return new Datei(
                r.id(),
                r.uploaderId(),
                r.titel(),
                r.uploadTime(),
                r.beschreibung(),
                r.dateiSystemName(),
                r.originalName()
        );
    }

    @Override
    public void save(Datei datei) {
        dateiDAO.save(toRecord(datei));
    }

    @Override
    public Optional<Datei> findByDateiSystemName(String name) {
        System.out.println("Suche Datei: " + name);
        DateiRecord record = dateiDAO.findByDateiSystemName(name);
        System.out.println("Gefunden: " + record);
        if (record == null) return Optional.empty();
        return Optional.of(toDomain(record));
    }

    @Override
    public List<Datei> findByUploaderId(String uploaderId) {
        return dateiDAO.findAllByUploaderId(uploaderId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void deleteByDateiSystemName(String name) {
        DateiRecord record = dateiDAO.findByDateiSystemName(name);
        if (record != null) {
            dateiDAO.delete(record);
        }
    }

    @Override
    public boolean existsByDateiSystemName(String name) {
        return dateiDAO.existsByDateiSystemName(name);
    }
}