package com.ThesisIsComing.iihk_89.application.Datei;

import com.ThesisIsComing.iihk_89.domain.datei.Datei;

import java.util.List;
import java.util.Optional;

public interface DateiRepository {
    void save(Datei datei);
    Optional<Datei> findByDateiSystemName(String dateiSystemName);
    List<Datei> findByUploaderId(String uploaderId);
    void deleteByDateiSystemName(String dateiSystemName);
    boolean existsByDateiSystemName(String dateiSystemName);
}
