package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.betreuer;

import org.springframework.data.relational.core.mapping.Table;

@Table("betreuer_fachgebiete")
public record BetreuerFachgebietRecord(
        String fachgebiet) {
}
