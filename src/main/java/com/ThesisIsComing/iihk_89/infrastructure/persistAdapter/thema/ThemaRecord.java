package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.thema;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

@Table("thema")
public record ThemaRecord(
    @Id Long id,
    String titel,

    @Column("betreuer_id")
    String betreuerId,
    String beschreibung,

    @MappedCollection(idColumn = "thema_id")
    Set<ThemaFachgebietRecord> fachgebiete,

    @MappedCollection(idColumn = "thema_id")
    Set<ThemaLinkRecord> links,

    @MappedCollection(idColumn = "thema_id")
    Set<ThemaDateiRecord> dateiSystemNamen,

    @MappedCollection(idColumn = "thema_id")
    Set<ThemaVeranstaltungRecord> veranstaltungen

    ){
    public ThemaRecord withId(Long id){
        return new ThemaRecord(id,this.titel,this.betreuerId,this.beschreibung,
                this.fachgebiete,this.links,this.dateiSystemNamen,this.veranstaltungen);
    }
}
