package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.betreuer;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("betreuer_datei_system_namen")
public record BetreuerDateiRecord(
        @Column("datei_system_name")
        String dateiSystemName
) {
}
