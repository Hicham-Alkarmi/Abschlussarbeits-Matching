package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.datei;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface DateiDAO
        extends CrudRepository<DateiRecord, Long> {

    DateiRecord findByDateiSystemName(String dateiSystemName);
    boolean existsByDateiSystemName(String dateiSystemName);
    List<DateiRecord> findAllByUploaderId(String uploaderId);
}
