package com.ThesisIsComing.iihk_89.infrastructure.aktivAdapter.web;

import com.ThesisIsComing.iihk_89.application.Datei.DateiDownloadService;
import com.ThesisIsComing.iihk_89.application.Datei.DateiLoeschenService;
import com.ThesisIsComing.iihk_89.application.Datei.DateiUploadDTO;
import com.ThesisIsComing.iihk_89.application.Datei.FormDTO;
import com.ThesisIsComing.iihk_89.application.Datei.UploadServices.DateiUploadZuBetreuerService;
import com.ThesisIsComing.iihk_89.application.Datei.UploadServices.DateiUploadZuThemaService;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;

@Controller
public class DateiController {
    private final DateiUploadZuBetreuerService uploadZuBetreuerService;
    private final DateiUploadZuThemaService uploadZuThemaService;
    private final DateiLoeschenService dateiLoeschenService;
    private final DateiDownloadService dateiDownloadService;

    public DateiController(
            DateiUploadZuBetreuerService uploadZuBetreuerService,
            DateiUploadZuThemaService uploadZuThemaService,
            DateiLoeschenService dateiLoeschenService,
            DateiDownloadService dateiDownloadService) {
        this.uploadZuBetreuerService = uploadZuBetreuerService;
        this.uploadZuThemaService = uploadZuThemaService;
        this.dateiLoeschenService = dateiLoeschenService;
        this.dateiDownloadService = dateiDownloadService;
    }

    //Upload zu Betreuer

    @GetMapping("/betreuer/datei-hochladen")
    @Secured("ROLE_BETREUER")
    public String dateiFormular(Model model) {
        model.addAttribute("form", new FormDTO("", ""));
        return "DateiUpload";
    }

    @PostMapping("/betreuer/datei-hochladen")
    @Secured("ROLE_BETREUER")
    public String dateiUpload(
            @RequestParam("datei") MultipartFile datei,
            @Valid @ModelAttribute("form") FormDTO form,
            BindingResult bindingResult,
            @AuthenticationPrincipal OAuth2User user,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "DateiUpload";
        }
        try {
            String betreuerId = gibGithubId(user);
            DateiUploadDTO dto = new DateiUploadDTO(
                    datei.getBytes(),
                    datei.getOriginalFilename(),
                    datei.getSize(),
                    betreuerId,
                    form.titel(),
                    form.beschreibung()
            );
            uploadZuBetreuerService.execute(dto);
            return "redirect:/betreuer/alle-themen";
        } catch (Exception e) {
            model.addAttribute("fehler", e.getMessage());
            model.addAttribute("form", form);
            return "DateiUpload";
        }
    }

    //Upload zu Thema

    @GetMapping("/betreuer/thema/{themaId}/datei-hochladen")
    @Secured("ROLE_BETREUER")
    public String dateiZuThemaFormular(@PathVariable Long themaId,
                                       Model model) {
        model.addAttribute("form", new FormDTO("", ""));
        model.addAttribute("themaId", themaId);
        return "DateiUpload";
    }

    @PostMapping("/betreuer/thema/{themaId}/datei-hochladen")
    @Secured("ROLE_BETREUER")
    public String dateiZuThemaUpload(
            @PathVariable Long themaId,
            @RequestParam("datei") MultipartFile datei,
            @Valid @ModelAttribute("form") FormDTO form,
            BindingResult bindingResult,
            @AuthenticationPrincipal OAuth2User user,
            Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("themaId", themaId);
            return "DateiUpload";
        }
        try {
            String betreuerId = gibGithubId(user);
            DateiUploadDTO dto = new DateiUploadDTO(
                    datei.getBytes(),
                    datei.getOriginalFilename(),
                    datei.getSize(),
                    betreuerId,
                    form.titel(),
                    form.beschreibung()
            );
            uploadZuThemaService.execute(dto, themaId);
            return "redirect:/betreuer/alle-themen";
        } catch (Exception e) {
            model.addAttribute("fehler", e.getMessage());
            model.addAttribute("form", form);
            model.addAttribute("themaId", themaId);
            return "DateiUpload";
        }
    }

    // Löschen

    @PostMapping("/betreuer/delete-datei/{dateiSystemName}")
    @Secured("ROLE_BETREUER")
    public String deleteDatei(
            @AuthenticationPrincipal OAuth2User user,
            @PathVariable String dateiSystemName) {
        try {
            String githubId = gibGithubId(user);
            dateiLoeschenService.execute(dateiSystemName, githubId);
            return "redirect:/betreuer/alle-themen";
        } catch (Exception e) {
            return "redirect:/betreuer/alle-themen?fehler="
                    + e.getMessage();
        }
    }

    //Download

    @GetMapping("/datei/download/{dateiSystemName}")
    public ResponseEntity<byte[]> dateiDownload(
            @PathVariable String dateiSystemName) {
        try {
            byte[] inhalt = dateiDownloadService.execute(dateiSystemName);
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION,
                            "attachment; filename=\"" + dateiSystemName + "\"")
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(inhalt);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/datei/anzeigen/{dateiSystemName}")
    public String dateiAlsHtmlAnzeigen(
            @PathVariable String dateiSystemName,
            Model model) {
        try {
            byte[] inhalt = dateiDownloadService.execute(dateiSystemName);
            String text = new String(inhalt, StandardCharsets.UTF_8);

            MutableDataSet options = new MutableDataSet();
            Parser parser = Parser.builder(options).build();
            HtmlRenderer renderer = HtmlRenderer.builder(options).build();
            String html = renderer.render(parser.parse(text));

            model.addAttribute("inhalt", html);
            model.addAttribute("dateiName", dateiSystemName);
            return "DateiAnzeige";
        } catch (Exception e) {
            model.addAttribute("fehler", e.getMessage());
            return "DateiAnzeige";
        }
    }

        //Hilfsmethode
        private String gibGithubId (OAuth2User user){
            return String.valueOf((Integer) user.getAttribute("id"));
        }
    }
