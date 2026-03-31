package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.betreuer;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.MappedCollection;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;
import java.util.Set;


@Table("betreuer")
public record BetreuerRecord (
        @Id Long id,

        @Column("github_id")
        String githubId,
        String vorname,
        String nachname,
        String email,

        //@Version
       // Integer version,

        @Column("raum_nr")
        String raumNr,

        @MappedCollection(idColumn = "betreuer_id")
        Set<BetreuerFachgebietRecord> fachgebiete,

        @MappedCollection(idColumn = "betreuer_id")
        Set<BetreuerLinkRecord> links,

        @MappedCollection(idColumn = "betreuer_id")
        Set<BetreuerDateiRecord> dateiSystemNamen
) {}

/*Schritt 1 – Rehydrierung Konstruktor in Betreuer.java:
Ziel: Domain kann aus DB-Daten wiederhergestellt werden
→ ohne update Methoden zu missbrauchen
→ ohne Spring Annotationen in Domain

Schritt 2 – Records in Infrastructure:
Ziel: DB-Repräsentation von Domain trennen
→ @Id, @Table, @MappedCollection nur hier!
→ Domain bleibt sauber!
→ Records = technische Datencontainer für DB

Schritt 3 – BetreuerSpringRepo + JdbcBetreuerRepo:
Ziel: Übersetzer zwischen Domain und DB
→ BetreuerSpringRepo: Spring macht DB-Arbeit
→ JdbcBetreuerRepo: übersetzt Betreuer ↔ Record
→ Application Layer kennt nur BetreuerRepository Interface!
*
*
*
* */