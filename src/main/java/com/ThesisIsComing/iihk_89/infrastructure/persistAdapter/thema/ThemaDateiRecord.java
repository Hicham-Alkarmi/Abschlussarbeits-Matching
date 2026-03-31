package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.thema;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("thema_datei_system_name")
public record ThemaDateiRecord(
        @Column("datei_system_name")
        String dateiSystemName) {
}
