package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.web;

import com.ThesisIsComing.iihk_89.application.Datei.DateiLadenService;
import com.ThesisIsComing.iihk_89.application.thema.ThemaAnzeigenService;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class ThemaAnzeigenController {

    private final ThemaAnzeigenService themaAnzeigenService;
    private final DateiLadenService dateiLadenService;

    public ThemaAnzeigenController(
            ThemaAnzeigenService themaAnzeigenService,
            DateiLadenService dateiLadenService) {
        this.themaAnzeigenService = themaAnzeigenService;
        this.dateiLadenService = dateiLadenService;
    }

    @GetMapping("/thema/{themaId}")
    public String themaAnzeigen(@PathVariable Long themaId,
                                Model model) {
        try {
            Thema thema = themaAnzeigenService.execute(themaId);
            model.addAttribute("thema", thema);
        } catch (IllegalArgumentException e) {
            model.addAttribute("fehler", e.getMessage());
            return "ThemaDetail";
        }

        // Dateien separat - kein Crash wenn Fehler:
        try {
            Thema thema = (Thema) model.getAttribute("thema");
            model.addAttribute("dateien",
                    dateiLadenService.ladeAlleBySystemNamen(
                            thema.getDateiSystemNamen()));
        } catch (Exception e) {
            model.addAttribute("dateien", List.of());
        }

        return "ThemaDetail";
    }
}