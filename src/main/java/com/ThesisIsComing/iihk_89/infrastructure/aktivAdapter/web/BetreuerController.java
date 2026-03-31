package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.web;

import com.ThesisIsComing.iihk_89.application.betreuer.BetreuerEditDTO;
import com.ThesisIsComing.iihk_89.application.betreuer.BetreuerEditService;
import com.ThesisIsComing.iihk_89.application.betreuer.BetreuerProfilLadenService;
import com.ThesisIsComing.iihk_89.application.thema.*;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Secured("ROLE_BETREUER")
@RequestMapping("/betreuer")
public class BetreuerController {

    private final ThemenloeschenService themenloeschenService;
    private final ThemaErstellenService themaErstellenService;
    private final BetreuerThemenAnzeigenService betreuerThemenAnzeigenService;
    private final BetreuerEditService betreuerEditService;
    private final BetreuerProfilLadenService betreuerProfilLadenService;
    private final ThemaBearbeitenService themaBearbeitenService;
    private final ThemaProfilLadenService themaProfilLadenService;

    public BetreuerController(
            ThemenloeschenService themenloeschenService,
            ThemaErstellenService themaErstellenService,
            BetreuerThemenAnzeigenService betreuerThemenAnzeigenService,
            BetreuerEditService betreuerEditService,
            BetreuerProfilLadenService betreuerProfilLadenService,
            ThemaBearbeitenService themaBearbeitenService,
            ThemaProfilLadenService themaProfilLadenService) {
        this.themenloeschenService = themenloeschenService;
        this.themaErstellenService = themaErstellenService;
        this.betreuerThemenAnzeigenService = betreuerThemenAnzeigenService;
        this.betreuerEditService = betreuerEditService;
        this.betreuerProfilLadenService = betreuerProfilLadenService;
        this.themaBearbeitenService = themaBearbeitenService;
        this.themaProfilLadenService = themaProfilLadenService;
    }

    // Themen

    @GetMapping("/alle-themen")
    public String themenAnzeigen(@AuthenticationPrincipal OAuth2User user,
                                 Model model) {
        String betreuerId = gibGithubId(user);
        List<Thema> alleThemen =
                betreuerThemenAnzeigenService.execute(betreuerId);
        model.addAttribute("alleThemen", alleThemen);
        if (alleThemen.isEmpty()) {
            model.addAttribute("message", "Noch keine Themen vorhanden");
        }
        return "AlleThemen";
    }

    @GetMapping("/thema-erstellen")
    public String themaErstellenForm(@AuthenticationPrincipal OAuth2User user,
                                     Model model) {
        String betreuerId = gibGithubId(user);
        model.addAttribute("themaDto",
                new ThemaDTO("", "", betreuerId, "", "", "", ""));
        return "ThemaForm";
    }

    @PostMapping("/thema-erstellen")
    public String themaErstellen(
            @ModelAttribute("themaDto") ThemaDTO dto,
            Model model) {
        try {
            themaErstellenService.execute(dto);
            return "redirect:/betreuer/alle-themen";
        } catch (IllegalArgumentException e) {
            model.addAttribute("fehler", e.getMessage());
            model.addAttribute("themaDto", dto);
            return "ThemaForm";
        }
    }

    @PostMapping("/thema-loeschen/{themaId}")
    public String themaLoeschen(@PathVariable Long themaId) {
        themenloeschenService.execute(themaId);
        return "redirect:/betreuer/alle-themen";
    }

    @GetMapping("/thema-bearbeiten/{themaId}")
    public String themaBearbeitenForm(@PathVariable Long themaId,
                                      Model model) {
        ThemaDTO dto = themaProfilLadenService.execute(themaId);
        model.addAttribute("themaDto", dto);
        model.addAttribute("themaId", themaId);
        return "ThemaBearbeiten";
    }

    @PostMapping("/thema-bearbeiten/{themaId}")
    public String themaBearbeiten(@PathVariable Long themaId,
                                  @ModelAttribute("themaDto") ThemaDTO dto,
                                  Model model) {
        try {
            themaBearbeitenService.execute(themaId, dto);
            return "redirect:/betreuer/alle-themen";
        } catch (Exception e) {
            model.addAttribute("fehler", e.getMessage());
            model.addAttribute("themaDto", dto);
            model.addAttribute("themaId", themaId);
            return "ThemaBearbeiten";
        }
    }

    // Profil

    @GetMapping("/edit-profile")
    public String betreuerEditAnzeigen(@AuthenticationPrincipal OAuth2User user,
                                       Model model) {
        String githubId = gibGithubId(user);
        BetreuerEditDTO dto = betreuerProfilLadenService.execute(githubId);
        model.addAttribute("editDto", dto);
        return "BetreuerProfil";
    }

    @PostMapping("/edit-profile")
    public String betreuerEditSpeichern(
            @AuthenticationPrincipal OAuth2User user,
            @Valid @ModelAttribute("editDto") BetreuerEditDTO dto,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "BetreuerProfil";
        }
        try {
            String githubId = gibGithubId(user);
            betreuerEditService.execute(githubId, dto);
            return "redirect:/betreuer/edit-profile";
        } catch (Exception e) {
            model.addAttribute("fehler", e.getMessage());
            return "BetreuerProfil";
        }
    }


    private String gibGithubId(OAuth2User user) {
        return String.valueOf((Integer) user.getAttribute("id"));
    }
}