package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.application.thema.ThemaRepository;
import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import org.springframework.stereotype.Service;

@Service
public class BetreuerLoeschenService {
    private final BetreuerRepository betreuerRepository;
    private final ThemaRepository themaRepository;

     public BetreuerLoeschenService(BetreuerRepository betreuerRepository,
     ThemaRepository themaRepository){
         this.betreuerRepository=betreuerRepository;
         this.themaRepository=themaRepository;
     }

     public void execute(String githubId){
         if (!betreuerRepository.existsByGithubId(githubId)) {
             throw new IllegalArgumentException(
                     "Betreuer mit Id " + githubId + " existiert nicht!");
         }
         themaRepository.findAllByBetreuerId(githubId)
                 .forEach(thema ->
                         themaRepository.delete(thema.getId()));

         betreuerRepository.deleteByGithubId(githubId);
     }
}
