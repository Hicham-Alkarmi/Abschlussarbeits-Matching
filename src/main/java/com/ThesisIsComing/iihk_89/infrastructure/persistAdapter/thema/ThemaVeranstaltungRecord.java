package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.thema;

import org.springframework.data.relational.core.mapping.Table;

@Table("thema_veranstaltungen")
public record ThemaVeranstaltungRecord(
        String veranstaltung
) {
}
