package com.ThesisIsComing.iihk_89.application.betreuer;

import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import com.ThesisIsComing.iihk_89.domain.betreuer.BetreuerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class AlleBetreuerAnzeigenService {
    private final BetreuerRepository betreuerRepository;

    public AlleBetreuerAnzeigenService(BetreuerRepository betreuerRepository){
        this.betreuerRepository=betreuerRepository;
    }

    public List<Betreuer> execute(){
        return betreuerRepository.findAll();
    }
}
