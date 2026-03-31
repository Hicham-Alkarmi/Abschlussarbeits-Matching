package com.ThesisIsComing.iihk_89.domainService;


import com.ThesisIsComing.iihk_89.domain.betreuer.Betreuer;
import com.ThesisIsComing.iihk_89.domain.shared.Fachgebiet;
import com.ThesisIsComing.iihk_89.domain.thema.Thema;
import com.ThesisIsComing.iihk_89.domain.thema.Veranstaltung;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MatchingBerechner {

    public List<MatchErgebnis> berechne(
            String fachgebieteInput,
            String veranstaltungenInput,
            List<Thema> alleThemen,
            List<Betreuer> alleBetreuer) {

        List<Fachgebiet> fachgebiete = parseFachgebiete(fachgebieteInput);
        List<Veranstaltung> veranstaltungen = parseVeranstaltungen(veranstaltungenInput);

        List<MatchErgebnis> ergebnisse = new ArrayList<>();

        for (Thema thema : alleThemen) {
            int score = 0;
            for (Fachgebiet f : thema.getFachgebiete()) {
                if (fachgebiete.contains(f)) score++;
            }
            for (Veranstaltung v : thema.getEmpfohleneVeranstaltungen()) {
                if (veranstaltungen.contains(v)) score++;
            }
            if (score > 0) {
                ergebnisse.add(new MatchErgebnis(thema, null, score));
            }
        }

        for (Betreuer betreuer : alleBetreuer) {
            int score = 0;
            for (Fachgebiet f : betreuer.getFachgebiete()) {
                if (fachgebiete.contains(f)) score++;
            }
            if (score > 0) {
                ergebnisse.add(new MatchErgebnis(null, betreuer, score));
            }
        }

        Collections.sort(ergebnisse);
        return ergebnisse;
    }

    private List<Fachgebiet> parseFachgebiete(String input) {
        if (input == null || input.isBlank()) return List.of();
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Fachgebiet::new)
                .toList();
    }

    private List<Veranstaltung> parseVeranstaltungen(String input) {
        if (input == null || input.isBlank()) return List.of();
        return Arrays.stream(input.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .map(Veranstaltung::new)
                .toList();
    }
}