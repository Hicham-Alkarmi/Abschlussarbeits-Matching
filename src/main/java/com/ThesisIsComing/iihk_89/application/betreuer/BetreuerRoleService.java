package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import org.springframework.stereotype.Service;

@Service
public class BetreuerRoleService {
    private final BetreuerRepository betreuerRepository;

    public BetreuerRoleService(BetreuerRepository betreuerRepository){
        this.betreuerRepository=betreuerRepository;

    }

    public boolean execute(String githubId){
        return betreuerRepository.existsByGithubId(githubId);
    }
}
