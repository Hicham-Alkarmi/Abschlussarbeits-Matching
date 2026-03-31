package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.web;

import com.ThesisIsComing.iihk_89.application.betreuer.AlleBetreuerAnzeigenService;
import com.ThesisIsComing.iihk_89.application.matching.MatchingAnfrageDTO;
import com.ThesisIsComing.iihk_89.application.matching.MatchingService;
import com.ThesisIsComing.iihk_89.application.thema.AlleThemenAnzeigenService;
import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import com.ThesisIsComing.iihk_89.domainService.MatchErgebnis;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class StudiController {
    private final MatchingService matchingService;
    private final AlleBetreuerAnzeigenService alleBetreuerAnzeigenService;
    private final AlleThemenAnzeigenService alleThemenAnzeigenService;

    public StudiController(MatchingService matchingService,
                           AlleBetreuerAnzeigenService alleBetreuerAnzeigenService,
                           AlleThemenAnzeigenService alleThemenAnzeigenService) {
        this.matchingService=matchingService;
        this.alleBetreuerAnzeigenService = alleBetreuerAnzeigenService;
        this.alleThemenAnzeigenService = alleThemenAnzeigenService;
    }
    @GetMapping("/matching")
    public String matchingAnzeigen(Model model){
        MatchingAnfrageDTO matchingAnfrageDTO = new MatchingAnfrageDTO("","");
        model.addAttribute("matchingAnfragenDTO",matchingAnfrageDTO);
        model.addAttribute("alleFachgebiete",
                matchingService.alleFachgebiete());
        return "Matching";
    }

    @PostMapping("/matching")
    public String matchingDurchfuehren(
            @RequestParam(value = "fachgebieteInput",
                    required = false)
            List<String> fachgebieteList,
            @RequestParam(defaultValue = "")
            String veranstaltungenInput,
            Model model) {
        try {

            String fachgebieteInput = fachgebieteList != null
                    ? String.join(", ", fachgebieteList)
                    : "";

            MatchingAnfrageDTO dto = new MatchingAnfrageDTO(
                    fachgebieteInput, veranstaltungenInput);
            List<MatchErgebnis> ergebnisse =
                    matchingService.execute(dto);
            model.addAttribute("ergebnisse", ergebnisse);
            model.addAttribute("dto", dto);
            model.addAttribute("alleFachgebiete",
                    matchingService.alleFachgebiete());
            return "Matching";
        } catch (Exception e) {
            model.addAttribute("fehler", e.getMessage());
            model.addAttribute("dto",
                    new MatchingAnfrageDTO("", ""));
            model.addAttribute("alleFachgebiete",
                    matchingService.alleFachgebiete());
            return "Matching";
        }
    }
    // Alle Betreuer

    @GetMapping("/alle-betreuer")
    public String alleBetreuerAnzeigen(Model model) {
        List<Betreuer> betreuer =
                alleBetreuerAnzeigenService.execute();
        model.addAttribute("betreuer", betreuer);
        if (betreuer.isEmpty()) {
            model.addAttribute("message",
                    "Noch keine Betreuer vorhanden");
        }
        return "AlleBetreuerSeite";
    }

    //  Alle Themen

    @GetMapping("/alle-themen")
    public String alleThemenAnzeigen(Model model) {
        List<Thema> themen = alleThemenAnzeigenService.execute();
        List<Betreuer> betreuer =
                alleBetreuerAnzeigenService.execute();
        model.addAttribute("themen", themen);
        model.addAttribute("betreuer", betreuer);
        if (themen.isEmpty()) {
            model.addAttribute("message",
                    "Noch keine Themen vorhanden");
        }
        return "AlleThemenSeite";
    }
}
