package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.web;

import com.ThesisIsComing.iihk_89.application.betreuer.BetreuerAnlegenDTO;
import com.ThesisIsComing.iihk_89.application.betreuer.BetreuerAnlegenService;
import com.ThesisIsComing.iihk_89.application.betreuer.AlleBetreuerAnzeigenService;
import com.ThesisIsComing.iihk_89.application.betreuer.BetreuerLoeschenService;
import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import jakarta.validation.Valid;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Controller
@RequestMapping("/admin")
public class AdminController {
    private  final BetreuerAnlegenService betreuerAnlegenService;
    private final AlleBetreuerAnzeigenService alleBetreuerAnzeigenService;
    private final BetreuerLoeschenService betreuerLoeschenService;

    public AdminController(BetreuerAnlegenService betreuerAnlegenService,
                           AlleBetreuerAnzeigenService alleBetreuerAnzeigenService,
                           BetreuerLoeschenService betreuerLoeschenService){
        this.betreuerAnlegenService=betreuerAnlegenService;
        this.alleBetreuerAnzeigenService = alleBetreuerAnzeigenService;
        this.betreuerLoeschenService =betreuerLoeschenService;
    }
    @GetMapping("/alle-betreuer")
    @Secured("ROLE_ADMIN")
    public String betreuerAnzeigen(OAuth2AuthenticationToken auth, Model model, @RequestParam(required = false) String fehler){
        String login = auth.getPrincipal().getAttribute("login");

        List<Betreuer> alleBetreuer = alleBetreuerAnzeigenService.execute();
        model.addAttribute("betreuer", alleBetreuer);
        if(alleBetreuer.isEmpty()){
            model.addAttribute("message", "es gibt noch keine Betreuer");
            model.addAttribute("name",login);
        }
        if(fehler != null){
            model.addAttribute("fehler", fehler);

        }
        return "alleBetreuer";
    }

    @GetMapping("/betreuer-anlegen")
    @Secured("ROLE_ADMIN")
    public String betreuerForm(Model model){
        model.addAttribute("betreuerDto", new  BetreuerAnlegenDTO("","","","",""));
        return "betreuerForm";
    }
    @PostMapping("/betreuer-anlegen")
    @Secured("ROLE_ADMIN")
    public String betreuerAnlegen(@ModelAttribute("betreuerDto") @Valid BetreuerAnlegenDTO dto,
                                  BindingResult bindingResult,
                                  Model model){
        if (bindingResult.hasErrors()) {
            return "betreuerForm";
        }
        try {
            betreuerAnlegenService.execute(dto);
            return "redirect:/admin/alle-betreuer";

        } catch (IllegalArgumentException e) {
            model.addAttribute("fehler", e.getMessage());
            model.addAttribute("betreuerDto", dto);
            return "betreuerForm";
        }
    }

    @PostMapping("/betreuer-loeschen/{githubId}")
    @Secured("ROLE_ADMIN")
    public String betreuerLoeschen(@PathVariable String githubId, Model model){
        try {
        betreuerLoeschenService.execute(githubId);

        return"redirect:/admin/alle-betreuer";
    }
        catch (IllegalArgumentException e){
            return "redirect:/admin/alle-betreuer?fehler=" + e.getMessage();
        }
    }
}