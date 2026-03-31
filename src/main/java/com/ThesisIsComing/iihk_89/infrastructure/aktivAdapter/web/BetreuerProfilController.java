package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.web;

import com.ThesisIsComing.iihk_89.application.Datei.DateiLadenService;
import com.ThesisIsComing.iihk_89.application.Datei.DateiRepository;
import com.ThesisIsComing.iihk_89.application.betreuer.BetreuerProfilAnzeigenService;
import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import com.ThesisIsComing.iihk_89.domain.datei.Datei;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class BetreuerProfilController {

    private final BetreuerProfilAnzeigenService betreuerProfilAnzeigenService;
    private final DateiLadenService dateiLadenService;

    public BetreuerProfilController(
            BetreuerProfilAnzeigenService betreuerProfilAnzeigenService,
            DateiLadenService dateiLadenService) {
        this.betreuerProfilAnzeigenService = betreuerProfilAnzeigenService;
        this.dateiLadenService = dateiLadenService;
    }

    @GetMapping("/betreuer-profil/{githubId}")
    public String betreuerProfilAnzeigen(
            @PathVariable String githubId,
            Model model) {
        Betreuer betreuer =
                betreuerProfilAnzeigenService.execute(githubId);
        model.addAttribute("betreuer", betreuer);
        try {
            model.addAttribute("dateien",
                    dateiLadenService.ladeAlleBySystemNamen(
                            betreuer.getDateiSystemNamen()));
        } catch (Exception e) {
            model.addAttribute("dateien", List.of());
        }
        return "BetreuerProfilSeite";
    }
}