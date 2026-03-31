package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.betreuer;

import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@Table("betreuer_links")
public record BetreuerLinkRecord(
        String url,

        @Column("anzeigender_text")
        String anzeigenderText
){
}
