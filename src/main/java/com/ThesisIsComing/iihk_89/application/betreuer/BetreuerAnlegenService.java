package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.*;
import org.springframework.stereotype.Service;

// validieren -> DTO zu domain -> betreuer -> save
@Service
public class BetreuerAnlegenService {

    private final BetreuerRepository betreuerRepository;

    public BetreuerAnlegenService(BetreuerRepository betreuerRepository) {
        this.betreuerRepository = betreuerRepository;
    }

    public void execute(BetreuerAnlegenDTO dto) {
        if (betreuerRepository.existsByGithubId(dto.githubId())) {
            throw new IllegalArgumentException("Betreuer mit dieser githubId existiert schon");
        }

        Name name = new Name(dto.vorname(), dto.nachname());
        Email email = new Email(dto.email());
        Raum raum = new Raum(dto.raumNr());
        Kontaktinfo kontaktinfo = new Kontaktinfo(raum, email);

        Betreuer betreuer = new Betreuer(null,dto.githubId(), name ,kontaktinfo);

        betreuerRepository.save(betreuer);

    }



}
