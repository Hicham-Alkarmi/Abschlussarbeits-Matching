package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.thema;

import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface ThemaDAO
        extends CrudRepository<ThemaRecord, Long> {

    List<ThemaRecord> findAllByBetreuerId(String betreuerId);
}