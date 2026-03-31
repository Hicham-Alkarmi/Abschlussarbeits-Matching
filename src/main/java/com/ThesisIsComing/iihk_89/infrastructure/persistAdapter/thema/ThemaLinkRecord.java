package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.thema;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("thema_links")
public record ThemaLinkRecord(
        String url,

        @Column("anzeigender_text")
        String anzeigenderText
){
}
