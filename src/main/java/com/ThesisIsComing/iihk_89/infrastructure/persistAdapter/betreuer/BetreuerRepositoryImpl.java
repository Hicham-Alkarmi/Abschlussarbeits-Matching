package com.ThesisIsComing.iihk_89.infrastructure.persistAdapter.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import com.ThesisIsComing.iihk_89.domain.betreuer.*;
import com.ThesisIsComing.iihk_89.domain.shared.Fachgebiet;
import com.ThesisIsComing.iihk_89.domain.shared.Link;
import org.springframework.stereotype.Repository;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class BetreuerRepositoryImpl implements BetreuerRepository {

    private final BetreuerDAO betreuerDAO;

    public BetreuerRepositoryImpl(BetreuerDAO betreuerDAO) {

        this.betreuerDAO = betreuerDAO;
    }

    // wandelt mein Betreuer zu einem record Objekt
    // so wird es dann in der db gespeichert (("Rehydrierung")) <-- WICHTIG
    private BetreuerRecord toRecord(Betreuer b) {
        Set<BetreuerFachgebietRecord> fachgebiete = b.getFachgebiete()
                .stream()
                .map(f -> new BetreuerFachgebietRecord(f.getFachgebiet()))
                .collect(Collectors.toSet());

        Set<BetreuerLinkRecord> links = b.getLinks()
                .stream()
                .map(l -> new BetreuerLinkRecord(
                        l.getUrl().toString(),
                        l.getAnzeigenderText()))
                .collect(Collectors.toSet());

        Set<BetreuerDateiRecord> dateien = b.getDateiSystemNamen()
                .stream()
                .map(BetreuerDateiRecord::new)
                .collect(Collectors.toSet());

        return new BetreuerRecord(
                b.getId(),
                b.getGithubId(),
                b.getName().getVorname(),
                b.getName().getNachname(),
                b.getKontaktinfo().getEmail().getEmail(),
                b.getKontaktinfo().getRaum().getRaumNr(),
                fachgebiete,
                links,
                dateien
        );
    }


    // das objekt aus der db wird geholt dann zu einem Betreuer objekt gemacht
    private Betreuer toDomain(BetreuerRecord r) {
        Set<Fachgebiet> fachgebiete = r.fachgebiete()
                .stream()
                .map(f -> new Fachgebiet(f.fachgebiet()))
                .collect(Collectors.toSet());

        Set<Link> links = r.links()
                .stream()
                .map(l -> new Link(
                        URI.create(l.url()),
                        l.anzeigenderText()))
                .collect(Collectors.toSet());

        Set<String> dateien = r.dateiSystemNamen()
                .stream()
                .map(BetreuerDateiRecord::dateiSystemName)
                .collect(Collectors.toSet());

        // Rehydrierung Konstruktor benutzen!
        return new Betreuer(
                r.id(),
                r.githubId(),
                new Name(r.vorname(), r.nachname()),
                new Kontaktinfo(
                        new Raum(r.raumNr()),
                        new Email(r.email())
                ),
                links,
                fachgebiete,
                dateien
        );
    }



    @Override
    public void save(Betreuer betreuer) {
        betreuerDAO.save(toRecord(betreuer));
    }

    @Override
    public Optional<Betreuer> findByGithubId(String githubId) {
        BetreuerRecord record = betreuerDAO.findByGithubId(githubId);
        if (record == null) return Optional.empty();
        return Optional.of(toDomain(record));
    }

    @Override
    public List<Betreuer> findAll() {
        return ((List<BetreuerRecord>) betreuerDAO.findAll())
                .stream()
                .map(this::toDomain)
                .toList();
    }

    @Override
    public boolean existsByGithubId(String githubId) {
        return betreuerDAO.existsByGithubId(githubId);
    }

    @Override
    public void deleteByGithubId(String githubId) {
        BetreuerRecord record = betreuerDAO.findByGithubId(githubId);
        if (record != null) {
            betreuerDAO.delete(record);
        }
    }
}
