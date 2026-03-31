package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.thema;

import com.ThesisIsComing.iihk_89.application.thema.ThemaRepository;
import com.ThesisIsComing.iihk_89.domain.shared.Fachgebiet;
import com.ThesisIsComing.iihk_89.domain.shared.Link;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import com.ThesisIsComing.iihk_89.domain.thema.Veranstaltung;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class ThemaRepositoryImpl implements ThemaRepository {

    private final ThemaDAO themaDAO;

    public ThemaRepositoryImpl(ThemaDAO themaDAO) {
        this.themaDAO = themaDAO;
    }


    private ThemaRecord toRecord(Thema t) {
        Set<ThemaFachgebietRecord> fachgebiete = t.getFachgebiete()
                .stream()
                .map(f -> new ThemaFachgebietRecord(f.getFachgebiet()))
                .collect(Collectors.toSet());

        Set<ThemaLinkRecord> links = t.getLinks()
                .stream()
                .map(l -> new ThemaLinkRecord(
                        l.getUrl().toString(),
                        l.getAnzeigenderText()))
                .collect(Collectors.toSet());

        Set<ThemaVeranstaltungRecord> veranstaltungen =
                t.getEmpfohleneVeranstaltungen()
                        .stream()
                        .map(v -> new ThemaVeranstaltungRecord(
                                v.getVeranstaltung()))
                        .collect(Collectors.toSet());

        Set<ThemaDateiRecord> dateien = t.getDateiSystemNamen()
                .stream()
                .map(ThemaDateiRecord::new)
                .collect(Collectors.toSet());

        return new ThemaRecord(
                t.getId(),
                t.getTitel(),
                t.getBetreuerId(),
                t.getBeschreibung(),
                fachgebiete,
                links,
                dateien,
                veranstaltungen
        );
    }


    private Thema toDomain(ThemaRecord r) {
        List<Fachgebiet> fachgebiete = r.fachgebiete()
                .stream()
                .map(f -> new Fachgebiet(f.fachgebiet()))
                .toList();

        List<Link> links = r.links()
                .stream()
                .map(l -> new Link(
                        URI.create(l.url()),
                        l.anzeigenderText()))
                .toList();

        List<Veranstaltung> veranstaltungen = r.veranstaltungen()
                .stream()
                .map(v -> new Veranstaltung(v.veranstaltung()))
                .toList();

        List<String> dateien = r.dateiSystemNamen()
                .stream()
                .map(ThemaDateiRecord::dateiSystemName)
                .toList();

        return new Thema(
                r.id(),
                r.titel(),
                r.betreuerId(),
                r.beschreibung(),
                fachgebiete,
                veranstaltungen,
                links,
                dateien
        );
    }


    @Override
    public Thema save(Thema thema) {
        ThemaRecord savedRecord = themaDAO.save(toRecord(thema));
        return toDomain(savedRecord);
    }

    @Override
    public Optional<Thema> findById(Long themaId) {
        return themaDAO.findById(themaId)
                .map(this::toDomain);
    }

    @Override
    public List<Thema> findAll() {
        return StreamSupport
                .stream(themaDAO.findAll().spliterator(), false)
                .map(this::toDomain)
                .toList();
    }

    @Override
    public List<Thema> findAllByBetreuerId(String betreuerId) {
        return themaDAO.findAllByBetreuerId(betreuerId)
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public void delete(Long themaId) {
        themaDAO.findById(themaId)
                .ifPresent(themaDAO::delete);
    }

}

