package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.*;
import org.springframework.stereotype.Service;

@Service
public class BetreuerEditService {
   private final BetreuerRepository betreuerRepository;

   public BetreuerEditService(BetreuerRepository betreuerRepository){
      this.betreuerRepository=betreuerRepository;
   }

   public void execute (String githubId, BetreuerEditDTO editDTO){

      Betreuer betreuer = betreuerRepository.findByGithubId(githubId)
                 .orElseThrow(() -> new IllegalArgumentException(
                         "Betreuer nicht gefunden: " + githubId));

      Name newName = new Name(editDTO.vorname(), editDTO.nachname());
      betreuer.updateName(newName);

      betreuer.updateKontaktinfo(new Kontaktinfo(
              new Raum(editDTO.raumNr()),
              new Email(editDTO.email())
      ));

      betreuer.updateFachgebiete(editDTO.fachgebiete());
      betreuer.updateLinks(editDTO.url(), editDTO.urlText());

      betreuerRepository.save(betreuer);

   }

}
